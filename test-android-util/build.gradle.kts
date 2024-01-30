plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    implementation(libs.tests.android.hilt)
    implementation(libs.tests.androidx.runner)
}
