package com.app.servicecrudapp.presentation.songform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.servicecrudapp.databinding.FragmentSongFormBinding
import com.app.servicecrudapp.presentation.songlist.SongListState
import com.app.servicecrudapp.presentation.songlist.SongListViewModel
import com.google.android.material.snackbar.Snackbar

// Formulario reutilizable: modo creación si songId está vacío, modo edición si tiene valor
class SongFormFragment : Fragment() {

    private val viewModel: SongListViewModel by activityViewModels()

    private var _binding: FragmentSongFormBinding? = null
    private val binding get() = _binding!!

    // Leer argumentos del Bundle; si no existen, los campos arrancan vacíos (modo creación)
    private val songId    by lazy { arguments?.getString("songId", "") ?: "" }
    private val songName  by lazy { arguments?.getString("songName", "") ?: "" }
    private val songTitle by lazy { arguments?.getString("songTitle", "") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val esEdicion = songId.isNotEmpty()

        if (esEdicion) {
            // Pre-llenar los campos con los datos existentes para edición
            binding.etSong.setText(songTitle)
            binding.etName.setText(songName)
        }

        binding.btnGuardar.setOnClickListener {
            val song = binding.etSong.text.toString().trim()
            val name = binding.etName.text.toString().trim()

            if (song.isEmpty() || name.isEmpty()) {
                Snackbar.make(binding.root, "Completa todos los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (esEdicion) {
                viewModel.updateSong(id = songId, name = name, song = song)
            } else {
                viewModel.createSong(name = name, song = song)
            }

            findNavController().popBackStack()
        }

        observeErrors()
    }

    private fun observeErrors() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state is SongListState.Error) {
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
