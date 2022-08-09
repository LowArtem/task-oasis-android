plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Configs.compileSdk
    defaultConfig.minSdk = Configs.minSdk
    defaultConfig.targetSdk = Configs.targetSdk
}

dependencies {
    implementation(AndroidCore.core)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    implementation(Security.cryptoKtx)

    implementation(Koin.core)
    implementation(Koin.android)

    testImplementation(TestDependencies.jUnit)

    implementation(project(Modules.model))
    implementation(project(Modules.utils))
}