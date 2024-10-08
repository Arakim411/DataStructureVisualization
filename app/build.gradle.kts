plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id(libs.plugins.hilt.get().pluginId)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.arakim.datastructurevisualization"

        // TODO get from ci/cd build
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildTypes {

        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    android.set(true)

    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

dependencies {

    implementation(project(":data:database"))
    implementation(project(":data:repository:data-structure"))
    implementation(project(":data:repository:visualization-set-up"))
    implementation(project(":ui:navigation"))
    implementation(project(":ui:util"))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)

    implementation(libs.androidx.work.ktx)

    implementation(libs.google.hilt.work)
    kapt(libs.google.hilt.compiler)
    kapt(libs.google.hilt.work.compiler)
    implementation(libs.google.hilt)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    debugImplementation(libs.bundles.composeDebug)
}
