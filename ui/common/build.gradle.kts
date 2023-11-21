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
    api(project(":ui:navigation:ui-controller"))
    implementation(project(":ui:util"))

    implementation(libs.compose.swipe)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.compose)
    implementation(platform(libs.androidx.compose.bom))
}
