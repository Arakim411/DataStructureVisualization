plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    api(project(":kotlin-util"))

    implementation(libs.google.dagger)
    implementation(libs.kotlinx.coroutines.core)
}
