package com.app.servicecrudapp.presentation.songlist

import com.app.servicecrudapp.domain.model.Song

// Estados posibles de la pantalla de lista — la UI reacciona a cada estado via LiveData
sealed class SongListState {
    object Loading : SongListState()
    data class Success(val songs: List<Song>) : SongListState()
    data class Error(val message: String) : SongListState()
}
