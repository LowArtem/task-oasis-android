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

    testImplementation(TestDependencies.jUnit)
}