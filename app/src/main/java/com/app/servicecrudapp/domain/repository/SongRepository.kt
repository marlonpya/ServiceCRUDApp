package com.app.servicecrudapp.domain.repository

import com.app.servicecrudapp.data.datasource.SongRemoteDataSource
import com.app.servicecrudapp.domain.mapper.SongMapper
import com.app.servicecrudapp.domain.model.Song

// Repositorio: única fuente de verdad para la presentación; aplica el mapper en cada operación
class SongRepository {

    private val dataSource = SongRemoteDataSource()

    suspend fun getSongs(): List<Song> =
        dataSource.getSongs().map { SongMapper.toDomain(it) }

    suspend fun getSongById(id: String): Song =
        SongMapper.toDomain(dataSource.getSongById(id))

    suspend fun createSong(song: Song): Song =
        SongMapper.toDomain(dataSource.createSong(SongMapper.toDto(song)))

    suspend fun updateSong(song: Song): Song =
        SongMapper.toDomain(dataSource.updateSong(song.id, SongMapper.toDto(song)))

    suspend fun deleteSong(id: String): Song =
        SongMapper.toDomain(dataSource.deleteSong(id))
}
