plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":ui:navigation:ui-controller"))

    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.compose)
    implementation(platform(libs.androidx.compose.bom))
}
