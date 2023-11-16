plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":kotlin-util"))

    implementation(libs.google.dagger)
    implementation(libs.kotlinx.coroutines.core)
}
