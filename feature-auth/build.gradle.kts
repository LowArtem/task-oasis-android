plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.7.0-1.0.6"
}

android {
    compileSdk = Configs.compileSdk
    defaultConfig.minSdk = Configs.minSdk
    defaultConfig.targetSdk = Configs.targetSdk

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCore
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

ksp {
    arg("compose-destinations.mode", "navgraphs")
    arg("compose-destinations.moduleName", "feature-auth")
}

dependencies {
    implementation(AndroidCore.core)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    implementation(AndroidCore.core)
    implementation(Kotlin.composeCore)
    implementation(Kotlin.composeMaterial)
    implementation(Kotlin.uiTooling)
    implementation(AndroidCore.lifecycleCore)
    implementation(AndroidCore.viewModel)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.compose)

    implementation(Navigation.navigationCompose)
    implementation(Navigation.navigationDestination)
    ksp(Navigation.navigationKsp)

    implementation(Retrofit.core)
    implementation(Retrofit.okHttpCore)
    implementation(Retrofit.okHttpLogging)
    implementation(Retrofit.converterGson)

    implementation(Security.cryptoKtx)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.jUnitExt)
    androidTestImplementation(TestDependencies.espressoCore)
    androidTestImplementation(TestDependencies.composeUi)

    debugImplementation(Debug.composeUiTooling)
    debugImplementation(Debug.composeUiTestManifest)

    implementation(project(Modules.model))
    implementation(project(Modules.data))
    implementation(project(Modules.utils))
    implementation(project(Modules.designSystem))
    implementation(project(Modules.uicomponents))
}