package com.challenge.themoviedb.presentation.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.challenge.themoviedb.databinding.MovieItemRowBinding
import com.challenge.themoviedb.domain.model.Movie

class DiscoverMoviesAdapter(private val movieClickListener: DiscoverMoviesEventHandler) :  ListAdapter<Movie, MovieItemViewHolder>(
    MovieItemDiffCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            MovieItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            movieClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieItemDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}