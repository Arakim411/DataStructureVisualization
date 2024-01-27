plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.tests.mockkCore)

    implementation(libs.tests.jupiter)
    implementation(libs.tests.jupiterParams)
    implementation(libs.tests.coroutine)
    implementation(kotlin("reflect"))
}
