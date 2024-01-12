plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

dependencies {
    implementation(project(":data:repository:data-structure:local-data-source"))
    implementation(project(":domain:data-structure"))

    implementation(libs.androidx.work.ktx)
    implementation(libs.google.hilt.work)
    kapt(libs.google.hilt.work.compiler)

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)
}
