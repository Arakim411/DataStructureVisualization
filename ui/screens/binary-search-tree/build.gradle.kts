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
    implementation(project(":android-util"))
    implementation(project(":domain:data-structure"))
    implementation(project(":kotlin-util"))
    implementation(project(":ui:common"))
    implementation(project(":ui:mvi"))
    implementation(project(":ui:util"))
    implementation(project(":ui:visualization-builder"))
    implementation(project(":ui:visualization-builder:set-up-picker"))

    implementation(libs.gson)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
