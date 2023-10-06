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

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
