plugins {
    kotlin("jvm") version "2.2.10"
    id("com.android.application") version "9.2.1" apply false
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    main {
        kotlin.srcDirs("kotlin")
        kotlin.exclude("app/**")
    }
}
