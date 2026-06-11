package com.app.servicecrudapp.data.datasource

import com.app.servicecrudapp.data.model.SongDto
import com.app.servicecrudapp.data.network.RetrofitClient

// Fuente de datos remota: delega directamente a SongApiService sin lógica adicional
class SongRemoteDataSource {

    private val api = RetrofitClient.songApiService

    suspend fun getSongs(): List<SongDto> = api.getSongs()

    suspend fun getSongById(id: String): SongDto = api.getSongById(id)

    suspend fun createSong(song: SongDto): SongDto = api.createSong(song)

    suspend fun updateSong(id: String, song: SongDto): SongDto = api.updateSong(id, song)

    suspend fun deleteSong(id: String): SongDto = api.deleteSong(id)
}
