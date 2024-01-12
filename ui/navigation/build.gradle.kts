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
    implementation(project(":ui:navigation:destination"))
    implementation(project(":ui:navigation:ui-controller"))
    implementation(project(":ui:screens:binary-search-tree"))
    implementation(project(":ui:screens:choose-data-structure"))
    implementation(project(":ui:screens:deleted-data-structures"))
    implementation(project(":ui:screens:hash-map"))
    implementation(project(":ui:util"))

    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
}
