plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compileVersion)
    buildToolsVersion(AndroidSdk.buildToolsVersion)

    defaultConfig {
        minSdkVersion(AndroidSdk.minVersion)
        targetSdkVersion(AndroidSdk.targetVersion)
        applicationId = "com.jsmirabal.animeinfo"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions { jvmTarget = "1.8" }
}

dependencies {
    implementation(Libraries.kotlinStdLib)

    // Androidx
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)

    // Google
    implementation(Libraries.googleMaterial)

    // Networking
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitConverter)
    implementation(Libraries.gson)

    // Threading
    implementation(Libraries.coroutinesCore)

    // Instrumentation testing
    androidTestImplementation(TestLibraries.jUnitExtension)
    androidTestImplementation(TestLibraries.espresso)

    // Unit testing
    testImplementation(TestLibraries.jUnitJupiterApi)
    testRuntimeOnly(TestLibraries.jUnitJupiterEngine)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kluent)
    testImplementation(Libraries.coroutinesAndroid)
    testImplementation(TestLibraries.coroutinesTest)

}
