#Requires -Version 5.1
[CmdletBinding()]
param(
    [ValidateSet('Debug', 'Release')]
    [string]$BuildType = 'Debug',
    [ValidateSet('rootphone_api30', 'pdfviewer_api28', 'pdfviewer_api26', 'pdfviewer_api24')]
    [string]$AvdName = 'rootphone_api30',
    [switch]$OnlyStart
)

$ErrorActionPreference = 'Stop'
$repoRoot = Split-Path -Parent $PSScriptRoot
$emulatorRoot = 'D:\Android\MobilePhone'
$buildSdk = 'D:\Android\Android SDK'
$adb = Join-Path $buildSdk 'platform-tools\adb.exe'
$emulator = Join-Path $emulatorRoot 'emulator\emulator.exe'

# 使用已有的模拟器环境，构建仍使用完整的 Android SDK。
$env:ANDROID_HOME = $emulatorRoot
$env:ANDROID_SDK_ROOT = $emulatorRoot
$env:ANDROID_AVD_HOME = Join-Path $emulatorRoot 'avd'
$env:ANDROID_EMULATOR_HOME = Join-Path $emulatorRoot '.emulator'
$env:ANDROID_USER_HOME = Join-Path $env:USERPROFILE '.android'

function Invoke-CheckedNative {
    param([string]$FilePath, [string[]]$Arguments)
    & $FilePath @Arguments
    if ($LASTEXITCODE -ne 0) { throw "Command failed (exit $LASTEXITCODE): $FilePath" }
}

try {
    foreach ($requiredPath in @($adb, $emulator, (Join-Path $env:ANDROID_AVD_HOME "$AvdName.ini"))) {
        if (-not (Test-Path -LiteralPath $requiredPath)) { throw "Required file missing: $requiredPath" }
    }

    Write-Host "[1/3] Starting/reusing $AvdName..." -ForegroundColor Cyan
    $deviceLines = @(& $adb devices)
    if ($LASTEXITCODE -ne 0) { throw 'Unable to list adb devices.' }
    $serial = $null
    foreach ($line in $deviceLines) {
        # 同一个 AVD 可能已经由其他项目在动态端口启动。
        if ($line -notmatch '^(emulator-\d+)\s+device(?:\s|$)') { continue }
        $candidate = $Matches[1]
        $names = @(& $adb -s $candidate emu avd name)
        if ($LASTEXITCODE -eq 0 -and $names -contains $AvdName) {
            $serial = $candidate
            break
        }
    }

    if (-not $serial) {
        $port = 5580
        while ($deviceLines -match "^emulator-$port\s" -or
               [System.Net.NetworkInformation.IPGlobalProperties]::GetIPGlobalProperties().GetActiveTcpListeners().Port -contains $port -or
               [System.Net.NetworkInformation.IPGlobalProperties]::GetIPGlobalProperties().GetActiveTcpListeners().Port -contains ($port + 1)) {
            $port += 2
            if ($port -gt 5680) { throw 'No free emulator port found.' }
        }
        $serial = "emulator-$port"
        $logDir = Join-Path $repoRoot '.gradle\emulator-logs'
        New-Item -ItemType Directory -Force -Path $logDir | Out-Null
        $stamp = Get-Date -Format 'yyyyMMdd-HHmmss-fff'
        # 隐藏启动器控制台；QEMU 子进程正常显示模拟器界面。不清除 AVD 数据。
        Start-Process -FilePath $emulator -ArgumentList @(
            '-avd', $AvdName, '-port', $port, '-gpu', 'software',
            '-no-snapshot', '-no-boot-anim', '-netdelay', 'none', '-netspeed', 'full'
        ) -WorkingDirectory $emulatorRoot -WindowStyle Hidden `
          -RedirectStandardOutput (Join-Path $logDir "$stamp-out.log") `
          -RedirectStandardError (Join-Path $logDir "$stamp-err.log") | Out-Null
    }

    $deadline = (Get-Date).AddMinutes(4)
    do {
        $previousPreference = $ErrorActionPreference
        $ErrorActionPreference = 'Continue'
        try {
            $bootState = @(& $adb -s $serial shell getprop sys.boot_completed 2>$null)
            $bootReady = $LASTEXITCODE -eq 0 -and ($bootState -join '').Trim() -eq '1'
        } finally { $ErrorActionPreference = $previousPreference }
        if ($bootReady) { break }
        # Windows 启动器会转交给 QEMU 子进程，以 Android 上线状态判断是否就绪。
        if ((Get-Date) -gt $deadline) { throw "Android boot timed out: $serial. Inspect .gradle\emulator-logs" }
        Start-Sleep -Seconds 2
    } while ($true)
    $actualName = @(& $adb -s $serial emu avd name)
    if ($LASTEXITCODE -ne 0 -or $actualName -notcontains $AvdName) { throw 'Unexpected emulator identity.' }
    Write-Host "Ready: $AvdName ($serial)." -ForegroundColor Green
    if ($OnlyStart) { exit 0 }

    $env:ANDROID_HOME = $buildSdk
    $env:ANDROID_SDK_ROOT = $buildSdk
    $env:JAVA_HOME = 'C:\Program Files\Java\jdk-21.0.12.1'
    $env:GRADLE_USER_HOME = Join-Path $env:USERPROFILE '.gradle'
    $javaTmp = Join-Path $repoRoot '.gradle\java-tmp'
    New-Item -ItemType Directory -Force -Path $javaTmp | Out-Null
    $env:JAVA_TOOL_OPTIONS = "$env:JAVA_TOOL_OPTIONS -Djdk.net.unixdomain.tmpdir=$javaTmp".Trim()
    $buildArguments = @("`:app:assemble$BuildType", '--console=plain')
    if ($BuildType -eq 'Release') {
        # 学习项目没有发布密钥；使用与 Debug 相同的本地密钥，便于覆盖安装。
        $debugKey = Join-Path $env:ANDROID_USER_HOME 'debug.keystore'
        if (-not (Test-Path -LiteralPath $debugKey)) {
            throw 'Run Debug once to create the local debug.keystore before running Release.'
        }
        $buildArguments += @(
            "-Pandroid.injected.signing.store.file=$debugKey",
            '-Pandroid.injected.signing.store.password=android',
            '-Pandroid.injected.signing.key.alias=androiddebugkey',
            '-Pandroid.injected.signing.key.password=android'
        )
    }

    Write-Host "[2/3] Building $BuildType..." -ForegroundColor Cyan
    Push-Location $repoRoot
    try { Invoke-CheckedNative (Join-Path $repoRoot 'gradlew.bat') $buildArguments }
    finally { Pop-Location }

    $variant = $BuildType.ToLowerInvariant()
    $apkDir = Join-Path $repoRoot "kotlin\app\build\outputs\apk\$variant"
    $metadata = Get-Content -LiteralPath (Join-Path $apkDir 'output-metadata.json') -Raw -Encoding UTF8 | ConvertFrom-Json
    if (@($metadata.elements).Count -ne 1) { throw 'Expected one APK output.' }
    $apk = Join-Path $apkDir $metadata.elements[0].outputFile
    Write-Host "[3/3] Installing and opening $($metadata.applicationId)..." -ForegroundColor Cyan
    # 覆盖安装保留数据；签名冲突时报错，不自动卸载。
    Invoke-CheckedNative $adb @('-s', $serial, 'install', '-r', $apk)
    Invoke-CheckedNative $adb @(
        '-s', $serial, 'shell', 'am', 'start', '-S', '-W',
        '-n', "$($metadata.applicationId)/.ui.TestActivity"
    )
    Write-Host "Ready: $BuildType on $serial. Save changes and press Ctrl+Shift+B to run again." -ForegroundColor Green
    exit 0
} catch {
    Write-Error $_ -ErrorAction Continue
    exit 1
}
