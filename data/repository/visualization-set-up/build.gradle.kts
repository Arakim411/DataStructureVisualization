plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

dependencies {

    implementation(project(":domain:visualization-set-up"))
    implementation(project(":data:repository:visualization-set-up:local-data-source"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)
}
