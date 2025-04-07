plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "anu.g35.sharebooks"
    compileSdk = 34

    defaultConfig {
        applicationId = "anu.g35.sharebooks"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    // for Robolectric
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.annotation)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // MPAndroidChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // For testing, Robolectric; get Context
    testImplementation("org.robolectric:robolectric:4.12.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.5.0")


}