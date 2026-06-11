package com.app.servicecrudapp.presentation.songlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.servicecrudapp.databinding.ItemSongBinding
import com.app.servicecrudapp.domain.model.Song

// Adaptador del RecyclerView: infla cada ítem y notifica al Fragment cuando se toca uno
class SongAdapter(
    private val onItemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var songs: List<Song> = emptyList()

    fun submitList(newSongs: List<Song>) {
        songs = newSongs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount() = songs.size

    inner class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.tvSongTitle.text  = song.song
            binding.tvArtistName.text = song.name
            binding.tvStudent.text = song.idStudent
            binding.root.setOnClickListener { onItemClick(song) }
        }
    }
}
