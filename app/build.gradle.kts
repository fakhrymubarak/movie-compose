plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.fakhry.movie_compose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fakhry.movie_compose"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    composeOptions {
        val compose_compiler_version = "1.4.8"
        kotlinCompilerExtensionVersion = compose_compiler_version
    }

    kotlinOptions {
        jvmTarget = "11"
    }

}


dependencies {
    // Components
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview)

    // Compose Testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil
    implementation(libs.coil.compose)

    // Room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.truth)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4") {
//        // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-debug#debug-agent-and-android
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }


    // Navigation Compose
    implementation(libs.navigation.compose)


    // Testing
    implementation(libs.androidx.test.core.ktx)
    implementation(libs.androidx.test.ext.junit.ktx)
//    androidTestImplementation ("androidx.test.ext:junit:$testJunitVersion")
//    androidTestImplementation ("androidx.test:rules:$androidXTestVersion")
//    androidTestImplementation ("androidx.arch.core:core-testing:$archLifecycleTestVersion")
//    testImplementation ("androidx.arch.core:core-testing:$archLifecycleTestVersion")

    // Instrumentation Testing
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.truth)

}