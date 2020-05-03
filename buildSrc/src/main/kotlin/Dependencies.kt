const val kotlinVersion = "1.3.72"
const val corutinesVersion = "1.3.4"

object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.3.1"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"

}

object AndroidSdk {
    const val minVersion = 24
    const val compileVersion = 29
    const val targetVersion = compileVersion
    const val buildToolsVersion = "29.0.3"
}

object Libraries {

    private const val jetpackVersion = "1.1.0"
    private const val constraintLayoutVersion = "1.1.2"
    private const val ktxVersion = "1.2.0"
    private const val retrofitVersion = "2.7.2"

    // Core
    const val kotlinStdLib     = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat        = "androidx.appcompat:appcompat:$jetpackVersion"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    const val ktxCore          = "androidx.core:core-ktx:$ktxVersion"

    // Google
    const val googleMaterial = "com.google.android.material:material:1.1.0"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofitConverter = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val gson = "com.google.code.gson:gson:2.8.6"

    // Threading
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$corutinesVersion"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$corutinesVersion"
}

object TestLibraries {
    private const val jUnitVersion = "5.6.0"

    // Unit testing
    const val jUnitJupiterApi = "org.junit.jupiter:junit-jupiter-api:$jUnitVersion"
    const val jUnitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:$jUnitVersion"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$corutinesVersion"
    const val mockk = "io.mockk:mockk:1.9.3"
    const val kluent = "org.amshove.kluent:kluent:1.14"

    // Instrumentation testing
    const val jUnitExtension = "androidx.test.ext:junit:1.1.1"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
}
