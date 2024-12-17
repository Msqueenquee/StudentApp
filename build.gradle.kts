// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()  // Repositories untuk Google Services dan Firebase
        mavenCentral()  // Repository Maven untuk dependensi lainnya
    }

    dependencies {
        // Google Services plugin untuk Firebase
        classpath("com.google.gms:google-services:4.4.0") // Versi plugin Firebase
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false // Plugin untuk Google Services (Firebase)
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir) // Task untuk membersihkan build
}
