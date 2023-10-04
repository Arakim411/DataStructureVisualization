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
    implementation(project(":ui:util"))
    implementation(project(":ui:navigation:ui-controller"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
}
