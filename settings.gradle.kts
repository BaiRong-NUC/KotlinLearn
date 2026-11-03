pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Learn"

include(":app")
project(":app").projectDir = file("kotlin/app")
