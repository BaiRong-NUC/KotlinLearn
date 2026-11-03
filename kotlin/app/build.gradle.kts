plugins {
    id("com.android.application")
}

repositories {
    google()
    mavenCentral()
}

android {
    namespace = "com.example.kotlinlearn"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.kotlinlearn"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
}
