plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.feup.coffee_order_application"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.feup.coffee_order_application"
        minSdk = 26
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.compose.material:material:1.6.3")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Okhttp3 for the POST requests
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}