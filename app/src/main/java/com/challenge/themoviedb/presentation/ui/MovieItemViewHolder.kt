package com.challenge.themoviedb.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.themoviedb.BuildConfig
import com.challenge.themoviedb.databinding.MovieItemRowBinding
import com.challenge.themoviedb.domain.model.Movie

class MovieItemViewHolder(private val binding: MovieItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        binding.movieOverview.text = movie.overview
        binding.movieRate.text = movie.rating.toString()
        Glide.with(binding.root)
            .load(BuildConfig.TMDB_BASE_IMAGES_URL.plus(movie.poster_path))
            .into(binding.moviePoster)
    }
}