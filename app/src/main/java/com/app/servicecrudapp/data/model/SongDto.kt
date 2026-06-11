package com.app.servicecrudapp.data.model

import com.google.gson.annotations.SerializedName

// Modelo de datos de la capa de red: mapea exactamente los campos del JSON de MockAPI
data class SongDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("song") val song: String,
    @SerializedName("id_student") val idStudent: String
)
