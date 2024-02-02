plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id(libs.plugins.hilt.get().pluginId)
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    defaultConfig {
        testInstrumentationRunner = "com.arakim.datastructurevisualization.testandroidutil.CustomTestRunner"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":domain:data-structure"))
    implementation(project(":ui:common"))
    implementation(project(":ui:mvi"))
    implementation(project(":ui:util"))
    implementation(project(":android-util"))
    implementation(project(":ui:navigation:destination"))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    androidTestImplementation(project(":test-android-util"))

    testImplementation(project(":test-data-generator"))
    testImplementation(project(":test-util"))

    androidTestImplementation(project(":test-data-generator"))
    androidTestImplementation(project(":test-util"))
    kaptAndroidTest(libs.google.hilt.compiler)

    testImplementation(libs.bundles.jvmTests)
    androidTestImplementation(libs.bundles.androidComposeTests)
    debugImplementation(libs.bundles.composeDebug)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
    implementation(kotlin("reflect"))
}
