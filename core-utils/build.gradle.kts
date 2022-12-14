plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Kotlin.coroutinesCore)
    implementation(Koin.core)

    implementation(Retrofit.core)
    implementation(Retrofit.okHttpCore)

    implementation(project(Modules.model))
}