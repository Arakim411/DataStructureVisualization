plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

dependencies {
    implementation(project(":data:repository:data-structure:local-data-source"))
    implementation(project(":domain:data-structure"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)
}
