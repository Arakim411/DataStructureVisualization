plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

dependencies {

    api(project(":android-util"))
    implementation(project(":domain:visualization-set-up"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
}
