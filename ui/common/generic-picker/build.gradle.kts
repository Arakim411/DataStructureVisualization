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
    implementation(project(":ui:common"))
    implementation(project(":ui:mvi"))
    implementation(project(":ui:util"))

    implementation(libs.colorpicker)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.bundles.compose)
}
