package com.app.servicecrudapp.data.network

import com.app.servicecrudapp.data.model.SongDto
import retrofit2.http.*

// Contrato de la API REST: cada función suspend corresponde a un endpoint de MockAPI
interface SongApiService {

    @GET("users")
    suspend fun getSongs(): List<SongDto>

    @GET("users/{id}")
    suspend fun getSongById(@Path("id") id: String): SongDto

    @POST("users")
    suspend fun createSong(@Body song: SongDto): SongDto

    @PUT("users/{id}")
    suspend fun updateSong(@Path("id") id: String, @Body song: SongDto): SongDto

    @DELETE("users/{id}")
    suspend fun deleteSong(@Path("id") id: String): SongDto
}
