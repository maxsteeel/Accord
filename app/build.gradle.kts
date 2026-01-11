@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "uk.max.accord"
    compileSdk = 36

    androidResources {
        generateLocaleConfig = true
    }

    defaultConfig {
        applicationId = "uk.max.accord"
        minSdk = 31
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
        prefab = true
    }

    packaging {
        jniLibs {
            // https://issuetracker.google.com/issues/168777344#comment11
            pickFirsts += "lib/arm64-v8a/libdlfunc.so"
            pickFirsts += "lib/armeabi-v7a/libdlfunc.so"
            pickFirsts += "lib/x86/libdlfunc.so"
            pickFirsts += "lib/x86_64/libdlfunc.so"
        }
    }

    signingConfigs {
        create("release") {
            val keystoreFileProp = project.findProperty("KEYSTORE_FILE") as String?
            val keystorePasswordProp = project.findProperty("KEYSTORE_PASSWORD") as String?
            val keyAliasProp = project.findProperty("KEY_ALIAS") as String?
            val keyPasswordProp = project.findProperty("KEY_PASSWORD") as String?

            if (keystoreFileProp != null && keystorePasswordProp != null && keyAliasProp != null && keyPasswordProp != null) {
                storeFile = file(keystoreFileProp)
                storePassword = keystorePasswordProp
                keyAlias = keyAliasProp
                keyPassword = keyPasswordProp
            }
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    lint {
        checkReleaseBuilds = false
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

dependencies {
    implementation(project(":libPhonograph"))
    implementation(project(":Cupertino"))
    implementation(project(":hificore"))
    implementation(project(":misc:alacdecoder"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.midi)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.mediarouter)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.concurrent.futures.ktx)
    implementation(libs.hiddenapibypass)
    implementation(libs.material)
    implementation(libs.coil)
    debugImplementation(libs.leakcanary.android)
}
