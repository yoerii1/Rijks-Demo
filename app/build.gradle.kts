import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    id("de.mannodermaus.android-junit5") version "1.8.2.1"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    id("kotlin-parcelize")
}

android {
    namespace = "nl.yoerivanhoek.rijksdemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "nl.yoerivanhoek.rijksdemo"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val apiKey = readApiKeyFromLocalProperties()
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

fun readApiKeyFromLocalProperties(): String {
    val properties = gradleLocalProperties(rootDir, providers)

    return properties.getProperty("API_KEY")
        ?: throw Exception("Please add the Rijks API_KEY to your local.properties")
}

dependencies {
    val composeVersion = "1.7.8"
    val koinVersion = "4.0.0"
    val chuckerVersion = "4.1.0"
    val pagingVersion = "3.3.6"

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.paging:paging-common-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:$pagingVersion")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("io.insert-koin:koin-core-viewmodel:$koinVersion")
    implementation("io.insert-koin:koin-core-viewmodel-navigation:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("androidx.navigation:navigation-compose:2.8.8")

    debugImplementation("com.github.chuckerteam.chucker:library:$chuckerVersion")
    androidTestImplementation("com.github.chuckerteam.chucker:library-no-op:$chuckerVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chuckerVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.4")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.4")
    testImplementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    testImplementation("app.cash.turbine:turbine:1.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}
