plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(project(":kotlin-util"))
    implementation(libs.kotlinx.coroutines.core)
}
