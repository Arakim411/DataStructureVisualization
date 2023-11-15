import com.android.build.api.dsl.CommonExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.hilt) apply false
}

allprojects {
    // TODO find way to get from libs
    plugins.apply("org.jlleitschuh.gradle.ktlint")
    afterEvaluate {
        (project.properties["android"] as? CommonExtension<*, *, *, *, *>)?.apply {
            setUpAndroid(project.getNameSpace())
        }
        tasks.withType(KotlinCompile::class.java).configureEach {
            println("setUp: ${this.name}")
            setUpKotlin()
        }
    }
}

fun CommonExtension<*, *, *, *, *>.setUpAndroid(nameSpace: String) {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = nameSpace

    if (buildFeatures.compose == true) {
        composeOptions.kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        lint {
            warningsAsErrors = false
            disable.addAll(
                listOf(
                    "GradleDependency",
                    "UnusedResources"
                )
            )
        }
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun KotlinCompile.setUpKotlin() {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

fun Project.getNameSpace(): String {
    val moduleDirectories = projectDir.toString().removePrefix(rootDir.toString())
    val nameSpace = moduleDirectories.replace("/", ".").replace("\\", ".").replace("-", "")

    return libs.versions.baseNameSpace.get().plus(nameSpace)
}
