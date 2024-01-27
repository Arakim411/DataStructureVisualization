plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id(libs.plugins.hilt.get().pluginId)
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

android {
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":domain:data-structure"))
    implementation(project(":ui:common"))
    implementation(project(":ui:mvi"))
    implementation(project(":ui:util"))
    implementation(project(":android-util"))
    implementation(project(":ui:navigation:destination"))

    testImplementation(project(":test-data-generator"))
    testImplementation(project(":test-util"))

    testImplementation(libs.tests.jupiter)
    testImplementation(libs.tests.jupiterApi)
    testImplementation(libs.tests.jupiterEngine)
    testImplementation(libs.tests.jupiterParams)
    testImplementation(libs.tests.assertk)
    testImplementation(libs.tests.mockkCore)
    testImplementation(libs.tests.mockkAgent)
    testImplementation(libs.tests.turbine)
    testImplementation(libs.tests.coroutine)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.composeDebug)
    implementation(platform(libs.androidx.compose.bom))
    implementation(kotlin("reflect"))
}
