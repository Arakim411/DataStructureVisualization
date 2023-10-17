plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlin.reflect)
}
