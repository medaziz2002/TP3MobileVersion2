plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "umontpellier.gl1.tp2partie2"
    compileSdk = 34

    defaultConfig {
        applicationId = "umontpellier.gl1.tp2partie2"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    // Firebase BoM (Bill of Materials) pour gérer les versions
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation ("com.firebaseui:firebase-ui-storage:7.1.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-appcheck") // Ajoutez App Check si nécessaire
    implementation("com.google.firebase:firebase-appcheck-debug") // Pour le débogage
    // Autres dépendances Firebase (si nécessaires)
    implementation("com.google.firebase:firebase-firestore-ktx") // Firestore (si utilisé)
    implementation("com.google.android.gms:play-services-location:21.0.1") // Services de localisation

    // Dépendances UI et support
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity:1.8.0")

    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.maplibre.gl:android-sdk:11.5.1")
    implementation("org.maplibre.gl:android-plugin-annotation-v9:2.0.0")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}