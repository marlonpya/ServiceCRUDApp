package com.app.servicecrudapp.domain.mapper

import com.app.servicecrudapp.data.model.SongDto
import com.app.servicecrudapp.domain.model.Song

// Transforma entre el modelo de red (SongDto) y el modelo de dominio (Song)
object SongMapper {

    // De la capa de datos hacia el dominio (lectura)
    fun toDomain(dto: SongDto) = Song(
        id = dto.id,
        name = dto.name,
        song = dto.song,
        idStudent = dto.idStudent
    )

    // Del dominio hacia la capa de datos (escritura: POST/PUT)
    fun toDto(domain: Song) = SongDto(
        id = domain.id,
        name = domain.name,
        song = domain.song,
        idStudent = domain.idStudent
    )
}
