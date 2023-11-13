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
    implementation(project(":ui:screens:binary-search-tree"))
    implementation(project(":ui:screens:choose-data-structure"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
