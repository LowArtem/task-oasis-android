plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version Versions.ksp
}

android {
    compileSdk = Configs.compileSdk

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCore
    }
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
    implementation(Navigation.navigationKsp)

    implementation(Retrofit.core)
    implementation(Security.cryptoKtx)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.jUnitExt)
    androidTestImplementation(TestDependencies.espressoCore)
    androidTestImplementation(TestDependencies.composeUi)

    debugImplementation(Debug.composeUiTooling)
    debugImplementation(Debug.composeUiTestManifest)

    implementation(project(Modules.model))
    implementation(project(Modules.utils))
}