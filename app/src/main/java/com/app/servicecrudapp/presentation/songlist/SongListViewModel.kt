package com.app.servicecrudapp.presentation.songlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.servicecrudapp.domain.model.Song
import com.app.servicecrudapp.domain.repository.SongRepository
import com.app.servicecrudapp.presentation.util.getHardwareIds
import kotlinx.coroutines.launch

// AndroidViewModel permite acceder al Application context para llamar a getHardwareIds()
class SongListViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<SongListState>()
    val state: LiveData<SongListState> = _state

    private val repository = SongRepository()

    fun loadSongs() {
        viewModelScope.launch {
            _state.value = SongListState.Loading
            try {
                val songs = repository.getSongs()
                _state.value = SongListState.Success(songs)
            } catch (e: Exception) {
                _state.value = SongListState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createSong(name: String, song: String) {
        // El id_student se obtiene del dispositivo y se agrega automáticamente — el usuario no lo ve
        val idStudent = getApplication<Application>().getHardwareIds()
        viewModelScope.launch {
            try {
                repository.createSong(Song(id = "", name = name, song = song, idStudent = idStudent))
                loadSongs()
            } catch (e: Exception) {
                _state.value = SongListState.Error(e.message ?: "Error al crear")
            }
        }
    }

    fun updateSong(id: String, name: String, song: String) {
        val idStudent = getApplication<Application>().getHardwareIds()
        viewModelScope.launch {
            try {
                repository.updateSong(Song(id = id, name = name, song = song, idStudent = idStudent))
                loadSongs()
            } catch (e: Exception) {
                _state.value = SongListState.Error(e.message ?: "Error al actualizar")
            }
        }
    }

    fun deleteSong(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteSong(id)
                loadSongs()
            } catch (e: Exception) {
                _state.value = SongListState.Error(e.message ?: "Error al eliminar")
            }
        }
    }
}
