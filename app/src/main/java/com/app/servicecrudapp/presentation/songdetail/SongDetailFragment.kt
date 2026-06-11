package com.app.servicecrudapp.presentation.songdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.servicecrudapp.R
import com.app.servicecrudapp.databinding.FragmentSongDetailBinding
import com.app.servicecrudapp.presentation.songlist.SongListState
import com.app.servicecrudapp.presentation.songlist.SongListViewModel
import com.google.android.material.snackbar.Snackbar

// Pantalla de detalle: muestra los datos recibidos via Bundle y gestiona editar/eliminar
class SongDetailFragment : Fragment() {

    private val viewModel: SongListViewModel by activityViewModels()

    private var _binding: FragmentSongDetailBinding? = null
    private val binding get() = _binding!!

    // Leer los argumentos del Bundle enviado desde SongListFragment
    private val songId    by lazy { requireArguments().getString("songId", "") }
    private val songName  by lazy { requireArguments().getString("songName", "") }
    private val songTitle by lazy { requireArguments().getString("songTitle", "") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar los datos sin hacer otra llamada a la API
        binding.tvSongTitle.text  = songTitle
        binding.tvArtistName.text = songName

        binding.btnEditar.setOnClickListener {
            // Pasar los mismos datos al formulario en modo edición
            val args = bundleOf(
                "songId"    to songId,
                "songName"  to songName,
                "songTitle" to songTitle
            )
            findNavController().navigate(R.id.action_detail_to_form, args)
        }

        binding.btnEliminar.setOnClickListener {
            mostrarConfirmacionEliminar()
        }

        observeErrors()
    }

    private fun mostrarConfirmacionEliminar() {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar canción")
            .setMessage("¿Seguro que deseas eliminar \"$songTitle\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.deleteSong(songId)
                findNavController().popBackStack()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // Observa errores para mostrar Snackbar si la operación de eliminar falla
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
