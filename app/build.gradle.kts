plugins {
    alias(libs.plugins.android.application)  // Plugin untuk aplikasi Android
    alias(libs.plugins.google.gms.google.services) // Plugin untuk Google Services (Firebase)
}

android {
    namespace = "com.example.studentapp2"
    compileSdk = 34  // Versi SDK yang digunakan untuk kompilasi

    defaultConfig {
        applicationId = "com.example.studentapp2"
        minSdk = 24  // Minimum SDK yang didukung
        targetSdk = 34  // Target SDK yang digunakan
        versionCode = 1  // Versi kode aplikasi
        versionName = "1.0"  // Nama versi aplikasi

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // Menonaktifkan minification
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"  // File untuk proguard
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8  // Kompatibilitas dengan Java 8
        targetCompatibility = JavaVersion.VERSION_1_8  // Kompatibilitas dengan Java 8
    }

    buildFeatures {
        viewBinding = true  // Mengaktifkan ViewBinding
    }
}

dependencies {
    // AndroidX Core Libraries
    implementation(libs.appcompat)  // Library untuk kompatibilitas backward
    implementation(libs.material)  // Material Design Components
    implementation(libs.constraintlayout)  // Layout untuk tampilan berbasis constraint
    implementation(libs.activity)  // Activity library untuk fungsionalitas activity

    // Lifecycle Components
    implementation(libs.lifecycle.livedata.ktx)  // LiveData KTX
    implementation(libs.lifecycle.viewmodel.ktx)  // ViewModel KTX

    // Navigation Component
    implementation(libs.navigation.fragment)  // Navigation Fragment library
    implementation(libs.navigation.ui)  // Navigation UI library

    // Firebase Authentication
    implementation(libs.firebase.auth)  // Firebase Auth untuk otentikasi pengguna

    // Firebase BOM (Mengelola versi Firebase secara konsisten)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))  // Gunakan Firebase BOM untuk dependensi
    implementation("com.google.firebase:firebase-auth")  // Firebase Auth untuk autentikasi pengguna

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database")  // Firebase Realtime Database

    // RecyclerView untuk daftar mata kuliah
    implementation("androidx.recyclerview:recyclerview:1.2.1")  // Library RecyclerView

    // Testing Libraries
    testImplementation(libs.junit)  // Dependensi untuk pengujian unit
    androidTestImplementation(libs.ext.junit)  // Dependensi untuk pengujian UI dengan JUnit
    androidTestImplementation(libs.espresso.core)  // Dependensi untuk pengujian UI dengan Espresso
}
