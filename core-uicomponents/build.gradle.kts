plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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

dependencies {
    implementation(AndroidCore.core)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    implementation(AndroidCore.core)
    implementation(Kotlin.composeCore)
    implementation(Kotlin.composeMaterial)
    implementation(Kotlin.uiTooling)
    implementation(AndroidCore.lifecycleCore)
    implementation(AndroidCore.activityCompose)

    implementation(Navigation.navigationCompose)
    implementation(Navigation.navigationDestination)

    testImplementation(TestDependencies.jUnit)
    androidTestImplementation(TestDependencies.jUnitExt)
    androidTestImplementation(TestDependencies.espressoCore)
    androidTestImplementation(TestDependencies.composeUi)

    debugImplementation(Debug.composeUiTooling)
    debugImplementation(Debug.composeUiTestManifest)

    implementation(project(Modules.designSystem))
    implementation(project(Modules.model))
    implementation(project(Modules.utils))
}