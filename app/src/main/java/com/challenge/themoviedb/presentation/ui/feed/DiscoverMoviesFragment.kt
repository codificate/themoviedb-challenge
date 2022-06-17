package com.challenge.themoviedb.presentation.ui.feed

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.themoviedb.databinding.FragmentDiscoverMoviesBinding
import com.challenge.themoviedb.domain.DataResource
import com.challenge.themoviedb.presentation.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesFragment : Fragment(), DiscoverMoviesEventHandler {

    private lateinit var _binding: FragmentDiscoverMoviesBinding
    private val binding by lazy { _binding }
    private val moviesViewModel: MoviesViewModel by viewModels()

    private val adapter = DiscoverMoviesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel.getPopularMovies()
        initMovies()
        observeViewModel()
        binding.movieRefreshContainer.setOnRefreshListener { moviesViewModel.getPopularMovies() }
    }

    private fun initMovies() {
        binding.movieList.setHasFixedSize(true)
        binding.movieList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.movieList.adapter = adapter
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.movieList.layoutManager = GridLayoutManager(requireContext(),3);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.movieList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onMovieSelected(movieId: Int) {
        findNavController()
            .navigate(DiscoverMoviesFragmentDirections.actionGoToMovieDetail(movieId))
    }

    private fun observeViewModel() {
        moviesViewModel.popularMoviesPage.observe(viewLifecycleOwner) { moviePagesDataResource ->
            binding.movieRefreshContainer.isRefreshing = false
            when (moviePagesDataResource) {
                is DataResource.Success -> moviePagesDataResource.data?.let { adapter.submitList(it.movies) }
                    .also {
                        binding.movieList.visibility = VISIBLE
                        binding.moviesPullCallToAction.visibility = GONE
                    }
                is DataResource.Error -> {
                    binding.movieList.visibility = GONE
                    binding.moviesPullCallToAction.visibility = VISIBLE
                }
                else -> {}
            }
        }
    }
}

interface DiscoverMoviesEventHandler {

    fun onMovieSelected(movieId: Int)

}
