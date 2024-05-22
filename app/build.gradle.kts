

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.testtask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testtask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
// AndroidX AppCompat Library
    implementation ("androidx.appcompat:appcompat:1.4.2")

    // AndroidX RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    // AndroidX Fragment
    implementation ("androidx.fragment:fragment:1.4.1")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.13.2")

    // Gson
    implementation ("com.google.code.gson:gson:2.9.1")

    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}