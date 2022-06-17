package com.challenge.themoviedb.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.themoviedb.domain.DataResource
import com.challenge.themoviedb.domain.model.Genre
import com.challenge.themoviedb.domain.model.MovieDetail
import com.challenge.themoviedb.domain.model.MoviesPages
import com.challenge.themoviedb.domain.use_cases.movies.FetchGeneresUseCase
import com.challenge.themoviedb.domain.use_cases.movies.FetchMovieDetailUseCase
import com.challenge.themoviedb.domain.use_cases.movies.FetchPopularMoviesUseCase
import com.challenge.themoviedb.domain.use_cases.movies.FetchTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val fetchGeneresUseCase: FetchGeneresUseCase,
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase,
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase
) : ViewModel() {

    private val genres = mutableListOf<Genre>()

    private val mutablePopularMoviesList = MutableLiveData<DataResource<MoviesPages>>()
    val popularMoviesPage: LiveData<DataResource<MoviesPages>> = mutablePopularMoviesList

    private val mutableMovieDetail = MutableLiveData<DataResource<MovieDetail>>()
    val movieDetail: LiveData<DataResource<MovieDetail>> = mutableMovieDetail

    private val mutableTopRatedMoviesList = MutableLiveData<DataResource<MoviesPages>>()
    val topRatedMoviesPage: LiveData<DataResource<MoviesPages>> = mutableTopRatedMoviesList

    fun getGenresList() {
        fetchGeneresUseCase()
            .map { dataResourceResponse ->
                when (dataResourceResponse) {
                    is DataResource.Success -> dataResourceResponse.data?.let { genreList ->
                        if (genreList.isNotEmpty()) {
                            genres.removeAll(genres)
                        }
                        genreList.map { genre ->
                            genres.add(genre)
                        }
                    }
                    else -> {}
                }
            }
            .conflate()
            .launchIn(viewModelScope)
    }

    fun getPopularMovies() {
        fetchPopularMoviesUseCase()
            .map { mutablePopularMoviesList.postValue(it) }
            .conflate()
            .launchIn(viewModelScope)
    }

    fun findMovieDetailById(movieId: Int) {
        fetchMovieDetailUseCase(movieId)
            .map { mutableMovieDetail.postValue(it) }
            .conflate()
            .launchIn(viewModelScope)
    }

    fun getTopRatedMovies() {
        fetchTopRatedMoviesUseCase()
            .map { mutableTopRatedMoviesList.postValue(it) }
            .conflate()
            .launchIn(viewModelScope)
    }

}