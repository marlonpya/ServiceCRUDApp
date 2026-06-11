package com.app.servicecrudapp.presentation.songlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.servicecrudapp.R
import com.app.servicecrudapp.databinding.FragmentSongListBinding
import com.google.android.material.snackbar.Snackbar

// Pantalla de lista: observa el ViewModel y reacciona a cada estado de la UI
class SongListFragment : Fragment() {

    // ViewModel con scope de Activity para compartirlo con Detail y Form
    private val viewModel: SongListViewModel by activityViewModels()

    private var _binding: FragmentSongListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se llama solo una vez: evita recargar la lista al volver desde Detail o Form
        viewModel.loadSongs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeState()

        // El gesto de deslizar hacia abajo dispara una nueva llamada a la API
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadSongs()
        }

        // Navegar al formulario en modo creación (Bundle vacío)
        binding.btnNueva.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_form)
        }
    }

    private fun setupRecyclerView() {
        adapter = SongAdapter { song ->
            val args = bundleOf(
                "songId"    to song.id,
                "songName"  to song.name,
                "songTitle" to song.song
            )
            findNavController().navigate(R.id.action_list_to_detail, args)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SongListState.Loading -> {
                    // El spinner del SwipeRefreshLayout indica la carga en curso
                    binding.swipeRefresh.isRefreshing = true
                }
                is SongListState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    adapter.submitList(state.songs)
                }
                is SongListState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
