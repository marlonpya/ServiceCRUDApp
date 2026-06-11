// Configuración del módulo :app — plugins, SDK, dependencias y ViewBinding
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.servicecrudapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.app.servicecrudapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true

        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Retrofit + Gson para llamadas a la API REST
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Coroutines para operaciones asíncronas sin bloquear el hilo principal
    implementation(libs.coroutines.android)

    // ViewModel y LiveData para el patrón MVVM
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    // SwipeRefreshLayout para el gesto de pull-to-refresh en la lista
    implementation(libs.swiperefreshlayout)

    // OkHttp logging interceptor para ver peticiones y respuestas en Logcat
    implementation(libs.okhttp.logging)

    // Navigation Component para la navegación entre Fragments
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
