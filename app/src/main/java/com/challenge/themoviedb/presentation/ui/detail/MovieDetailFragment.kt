package com.challenge.themoviedb.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.challenge.themoviedb.BuildConfig
import com.challenge.themoviedb.databinding.FragmentMovieDetailBinding
import com.challenge.themoviedb.domain.DataResource
import com.challenge.themoviedb.domain.model.MovieDetail
import com.challenge.themoviedb.presentation.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var _binding: FragmentMovieDetailBinding
    private val binding by lazy { _binding }
    private val moviesViewModel: MoviesViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    private val adapter = MovieDetailProductionCompanyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel.findMovieDetailById(args.movieId)
        initRecyclerview()
        observeViewModel()
    }

    private fun initRecyclerview() {
        binding.movieCompanies.setHasFixedSize(true)
        binding.movieCompanies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.movieCompanies.adapter = adapter
    }

    private fun observeViewModel() {
        moviesViewModel.movieDetail.observe(
            viewLifecycleOwner,
            Observer { movieDetailDataResource ->
                when (movieDetailDataResource) {
                    is DataResource.Success -> {
                        movieDetailDataResource.data?.let { movieDetail ->
                            binding.movie = movieDetail
                            adapter.submitList(movieDetail.production_companies)
                            showPoster(movieDetail)
                        }
                    }
                    is DataResource.Error -> {
                        parentFragmentManager.popBackStack()
                    }
                    else -> {} // no-op
                }
            })
    }

    private fun showPoster(movieDetail: MovieDetail) =
        Glide.with(binding.root)
            .load(BuildConfig.TMDB_BASE_IMAGES_URL.plus(movieDetail.poster_path))
            .into(binding.moviePoster)
}