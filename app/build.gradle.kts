plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.daffa.swiftshift"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.daffa.swiftshift"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    val koin_version = "3.4.3"
//    val koin_android_version = "3.4.3"
//    val koin_android_compose_version = "3.4.3"

    // Window Manager
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.window:window:1.1.0")

    // https://mvnrepository.com/artifact/com.google.accompanist/accompanist-adaptive
    implementation("com.google.accompanist:accompanist-adaptive:0.33.2-alpha")

    // Compose dependencies
    implementation("androidx.wear.compose:compose-navigation:1.2.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    implementation("com.google.accompanist:accompanist-flowlayout:0.31.1-alpha")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Coil
    implementation("io.coil-kt:coil:2.2.2")
    implementation("com.google.accompanist:accompanist-coil:0.10.0")
    implementation("io.coil-kt:coil-compose:2.2.2")

    //koin
//    implementation ("io.insert-koin:koin-core:$koin_version")
//    implementation ("io.insert-koin:koin-android:$koin_android_version")
//    implementation( "io.insert-koin:koin-androidx-workmanager:$koin_android_version")
//    implementation ("io.insert-koin:koin-androidx-navigation:$koin_android_version")
//    implementation ("io.insert-koin:koin-androidx-compose:$koin_android_compose_version")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-android-compiler:2.48")
    ksp("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    // Palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    // UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Foundation
    implementation("androidx.compose.foundation:foundation:1.5.4")

    // Pager
    implementation("com.google.accompanist:accompanist-pager:0.12.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.12.0")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Shimmer
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.5")

    // Room
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    androidTestImplementation("androidx.room:room-testing:2.6.0")
}