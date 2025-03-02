

plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.jetbrains.kotlin.android)
        id ("com.google.dagger.hilt.android")
        id("org.jetbrains.kotlin.kapt")
        alias(libs.plugins.google.gms.google.services)
//        id("com.google.devtools.ksp")
        id("org.sonarqube")

    }

    android {
        namespace = "com.Singlee.forex"
        compileSdk = 35

        defaultConfig {
            applicationId = "com.Singlee.forex"
            minSdk = 26
            targetSdk = 35
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }


        // Use the `lint` block instead of the deprecated `lintOptions` block
    //    lint {
    //        checkAllWarnings = true // Check all issues, including those off by default
    //        abortOnError = true // Stop the build if errors are found
    //        warningsAsErrors = false // Treat warnings as warnings (not errors)
    //        baseline = file("lint-baseline.xml") // Use a baseline to ignore pre-existing issues
    //    }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
//        java {
//            toolchain {
//                languageVersion = JavaLanguageVersion.of(21) // Make sure to use JDK 21
//            }
//        }


        buildTypes {
            debug {
                signingConfig = signingConfigs.getByName("debug")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.10"
        }
    //    configurations.all {
    //        exclude(group = "io.grpc", module = "grpc-core")
    //    }


        packaging {
            resources {
                excludes += setOf(
                    "/META-INF/{AL2.0,LGPL2.1}",
                    "META-INF/NOTICE.md",
                    "META-INF/LICENSE.md",
                    "META-INF/NOTICE",
                    "META-INF/LICENSE"
                )
            }
        }
    }

    dependencies {

        androidTestImplementation ("androidx.test.ext:junit-ktx:1.2.1")
        androidTestImplementation ("android.arch.core:core-testing:1.1.1")
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.storage)
        implementation(libs.firebase.messaging)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        implementation(libs.androidx.runtime.livedata)
        implementation(libs.play.services.auth)

        implementation("androidx.compose.material:material:1.7.7")
//        implementation ("com.google.accompanist:accompanist-swiperefresh:0.35.1-alpha")

        implementation("androidx.compose.foundation:foundation:1.7.7") // Adjust version as needed

    //    implementation(libs.facebook.android.sdk)
    //    implementation("com.facebook.android:facebook-android-sdk:latest.release")



        implementation("com.facebook.android:facebook-login:17.0.0")

        implementation("com.google.code.gson:gson:2.11.0")

        implementation("androidx.credentials:credentials:1.3.0")
        implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
        implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

        implementation("androidx.navigation:navigation-compose:2.8.6")

        // Dagger Hilt
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-compiler:2.51.1")
        implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


        //mail sender
        implementation("com.sun.mail:android-mail:1.6.6")
        implementation("com.sun.mail:android-activation:1.6.7")


        implementation("com.github.bumptech.glide:glide:4.16.0")
        kapt("com.github.bumptech.glide:compiler:4.15.1")



        implementation("io.coil-kt:coil-compose:2.6.0")

        implementation("de.cketti.mailto:email-intent-builder:2.0.0")

    }
    kapt {
        correctErrorTypes = true
    }
