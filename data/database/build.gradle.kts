plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

android {

    defaultConfig {
        testInstrumentationRunner = "com.arakim.datastructurevisualization.testandroidutil.CustomTestRunner"
    }
}

dependencies {

    implementation(project(":data:repository:data-structure:local-data-source"))
    implementation(project(":data:repository:visualization-set-up:local-data-source"))

    kapt(libs.google.hilt.compiler)
    implementation(libs.google.hilt)

    // TODO find another way, probably modules dependency were incorrectly implemented
    androidTestImplementation(project(":domain:data-structure"))
    androidTestImplementation(project(":test-data-generator"))
    androidTestImplementation(project(":test-util"))
    androidTestImplementation(project(":test-android-util"))
    androidTestImplementation(project(":test-data-generator"))

    androidTestImplementation(libs.bundles.androidTests)
    androidTestImplementation(libs.tests.room)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
}
