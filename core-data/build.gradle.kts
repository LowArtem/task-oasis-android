plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    implementation(AndroidCore.core)
    implementation(Kotlin.coroutinesCore)
    implementation(Kotlin.coroutinesAndroid)

    testImplementation(TestDependencies.jUnit)
}