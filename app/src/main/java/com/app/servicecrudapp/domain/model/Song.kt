package com.app.servicecrudapp.domain.model

// Modelo de dominio: representa una canción sin dependencias de red ni de UI
data class Song(
    val id: String,
    val name: String,
    val song: String,
    val idStudent: String
)
