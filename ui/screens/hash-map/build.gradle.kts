plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id(libs.plugins.hilt.get().pluginId)
}

android {
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
    implementation(project(":ui:visualization-builder"))
    implementation(project(":ui:visualization-builder:set-up-picker"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.gson)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
