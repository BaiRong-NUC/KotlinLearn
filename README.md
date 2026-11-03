# Kotlin 学习示例

这个项目用于练习 Kotlin 基础语法、类和协程。每个示例文件可以单独运行，具体内容请查看文件中的代码和注释。

## 项目结构

```text
kotlin/
├── base/       # Kotlin 基础示例
├── class/      # 类相关示例
├── thread/     # 协程相关示例
└── app/        # Android 应用模块（Gradle 路径 :app）
.vscode/        # VS Code 配置和单文件运行脚本
```

## 环境

- JDK 21
- Gradle Wrapper 9.4.1（项目自带，首次使用可能需要联网下载）
- Kotlin 2.2.10
- kotlinx-coroutines-core 1.9.0
- Android SDK 36（构建 Android 应用时需要）

## 构建项目

在项目根目录运行：

```powershell
.\gradlew.bat build
```

Gradle 会从 Maven Central 获取项目依赖，编译结果放在 `build/`。

## Android 应用

在 VS Code 中按 **Ctrl+Shift+B**，选择 **Debug** 或 **Release**，即可自动启动或复用 `rootphone_api30`，编译、覆盖安装并打开应用。Release 使用本地调试密钥签名，供学习和本地运行。只启动模拟器时，使用“终端 → 运行任务 → Android: 仅启动已有模拟器”，可选择已有 AVD。

`kotlin/app` 是独立的 Android 应用模块，已接入根目录的 Gradle 工程。
入口文件为 `kotlin/app/src/main/java/com/example/kotlinlearn/ui/TestActivity.kt`，
布局文件为 `kotlin/app/src/main/res/layout/activity_test.xml`。

在项目根目录构建调试 APK：

```powershell
.\gradlew.bat :app:assembleDebug
```

APK 输出：`kotlin/app/build/outputs/apk/debug/app-debug.apk`。
连接手机或启动模拟器后，可以安装并启动：

```powershell
.\gradlew.bat :app:installDebug
& "$env:ANDROID_HOME/platform-tools/adb.exe" shell am start -n com.example.kotlinlearn/.ui.TestActivity
```

Android SDK 通过 `ANDROID_HOME` 或根目录 `local.properties` 中的 `sdk.dir` 配置。
使用 Android Studio 时打开项目根目录 `D:\Android\Learn`。
Android Activity 通过 Gradle 构建和运行；Code Runner 仅用于普通 Kotlin 示例。

## 运行单个示例

在 VS Code 中打开 `.kt` 文件，通过 Code Runner 运行；也可以在 Git Bash 中执行：

```bash
cd /d/Android/Learn/kotlin/thread
bash /d/Android/Learn/.vscode/run-kotlin.sh demo13.kt
```

运行脚本会将编译生成的 JAR 放到项目的 `.jar/` 目录。脚本中的项目路径、Kotlin 编译器路径和协程 JAR 路径是当前电脑的本地路径；在其他电脑上使用时，需要修改 `.vscode/run-kotlin.sh` 和 `.vscode/settings.json` 中对应的路径。
