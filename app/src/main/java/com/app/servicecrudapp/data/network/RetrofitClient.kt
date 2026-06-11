package com.app.servicecrudapp.data.network

import com.app.servicecrudapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton que construye y expone la instancia de Retrofit — se crea solo una vez (lazy)
object RetrofitClient {

    // La URL base debe terminar en "/" para que Retrofit resuelva correctamente los paths relativos
    private const val BASE_URL = "https://66c7791d732bf1b79fa6a746.mockapi.io/songs/"

    // Interceptor que imprime en Logcat la URL, headers, body de cada request y response
    // Solo activo en builds de debug para no exponer datos en producción
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val songApiService: SongApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongApiService::class.java)
    }
}
