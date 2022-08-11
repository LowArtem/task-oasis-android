object Versions {
    // Android core
    const val androidCore = "1.8.0"
    const val lifecycleCore = "2.5.1"
    const val activityCompose = "1.5.1"

    // Splash screen
    const val splashScreen = "1.0.0"

    // Design
    const val appCompat = "1.4.2"
    const val material = "1.6.1"

    // Kotlin
    const val composeCore = "1.2.0"
    const val coroutines = "1.6.4"

    // Koin
    const val koin = "3.2.0"

    // Ksp
    const val ksp = "${Configs.kotlinVersion}-1.0.6"

    // Work manager
    const val workManager = "2.7.1"

    // Navigation
    const val composeNavigation = "2.4.2"
    const val composeDestination = "1.6.15-beta"

    // Security
    const val securityCrypto = "1.1.0-alpha03"

    // Retrofit & OkHttp
    const val retrofitCore = "2.9.0"
    const val okHttp = "4.10.0"

    // Glide
    const val glide = "4.13.2"

    // Accompanist
    const val accompanistCore = "0.25.0"

    // Test
    const val jUnit = "4.13.2"
    const val jUnitExt = "1.1.3"
    const val espressoCore = "3.4.0"
    const val mockk = "1.12.5"
    const val assertJ = "3.23.1"
}

object Configs {
    const val compileSdk = 32
    const val applicationId = "com.trialbot.taskoasis"
    const val minSdk = 24
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val kotlinVersion = "1.7.0"
}

object AndroidCore {
    const val core = "androidx.core:core-ktx:${Versions.androidCore}"
    const val lifecycleCore = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleCore}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleCore}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
}

object Design {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Security {
    const val cryptoKtx = "androidx.security:security-crypto-ktx:${Versions.securityCrypto}"
}

object Kotlin {
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val composeCore = "androidx.compose.ui:ui:${Versions.composeCore}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeCore}"
    const val uiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.composeCore}"
}

object Koin {
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
}

object WorkManager {
    const val core = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    const val test = "androidx.work:work-testing:${Versions.workManager}"
}

object Navigation {
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val navigationDestination = "io.github.raamcosta.compose-destinations:core:${Versions.composeDestination}"
    const val navigationKsp = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestination}"
    const val navigationAnimated = "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestination}"
}

object Retrofit {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofitCore}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitCore}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val okHttpCore = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
}

object Glide {
    const val core = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Accompanist {
    const val pager = "com.google.accompanist:accompanist-pager:${Versions.accompanistCore}"
    const val placeholder = "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanistCore}"
    const val swipeToRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanistCore}"
    const val systemUiController = "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanistCore}"
}

object TestDependencies {
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val composeUi = "androidx.compose.ui:ui-test-junit4:${Versions.composeCore}"
    const val mockkUnit = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroidInstrumented = "io.mockk:mockk-android:${Versions.mockk}"
    const val assertJCore = "org.assertj:assertj-core:${Versions.assertJ}"
}

object Debug {
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeCore}"
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeCore}"
}

object Modules {
    const val data = ":core-data"
    const val model = ":core-model"
    const val uiComponents = ":core-uicomponents"
    const val designSystem = ":core-designsystem"
    const val utils = ":core-utils"
    const val auth = ":feature-auth"
}