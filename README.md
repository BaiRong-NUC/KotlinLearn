# Kotlin 学习示例

这个项目用于练习 Kotlin 基础语法、类和协程。每个示例文件可以单独运行，具体内容请查看文件中的代码和注释。

## 项目结构

```text
kotlin/
├── base/       # Kotlin 基础示例
├── class/      # 类相关示例
└── thread/     # 协程相关示例
.vscode/        # VS Code 配置和单文件运行脚本
```

## 环境

- JDK 21
- Gradle Wrapper 9.4.1（项目自带，首次使用可能需要联网下载）
- Kotlin 2.2.10
- kotlinx-coroutines-core 1.9.0

## 构建项目

在项目根目录运行：

```powershell
.\gradlew.bat build
```

Gradle 会从 Maven Central 获取项目依赖，编译结果放在 `build/`。

## 运行单个示例

在 VS Code 中打开 `.kt` 文件，通过 Code Runner 运行；也可以在 Git Bash 中执行：

```bash
cd /d/Android/Learn/kotlin/thread
bash /d/Android/Learn/.vscode/run-kotlin.sh demo13.kt
```

运行脚本会将编译生成的 JAR 放到项目的 `.jar/` 目录。脚本中的项目路径、Kotlin 编译器路径和协程 JAR 路径是当前电脑的本地路径；在其他电脑上使用时，需要修改 `.vscode/run-kotlin.sh` 和 `.vscode/settings.json` 中对应的路径。
