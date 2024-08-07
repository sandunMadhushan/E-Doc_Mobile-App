plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.s22010304.e_doc"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.s22010304.e_doc"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("integer", "app_id", "1224919355")
        resValue(
            "string",
            "app_sign", "945d34b4a640ab37e1ecc3f62e91a9227ba85d02d74a079bfe7e407a3c091c12"
        )

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

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.car.ui:car-ui-lib:2.6.0")
    implementation("com.android.databinding:library:8.3.2")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.firebase:firebase-bom:33.0.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.firebaseui:firebase-ui-database:8.0.2")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging:23.0.0")
    implementation("com.karumi:dexter:6.2.3")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")
    implementation ("com.github.ZEGOCLOUD:zego_uikit_signaling_plugin_android:+")
    implementation ("org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0")

}