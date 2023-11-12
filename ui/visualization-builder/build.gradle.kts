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

    api(project(":ui:visualization-builder:visualization-engine"))
    implementation(project(":ui:util"))
    implementation(project(":ui:visualization-builder:set-up-picker"))

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
