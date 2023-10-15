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

    implementation(project(":ui:util"))
    api(project(":ui:visualization-builder:visualization-engine"))
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
