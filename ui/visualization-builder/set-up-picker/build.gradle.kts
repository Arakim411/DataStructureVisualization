plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":ui:common:generic-picker"))
    api(project(":ui:mvi"))
    implementation(project(":ui:util"))
    implementation(project(":ui:visualization-builder:visualization-core"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
