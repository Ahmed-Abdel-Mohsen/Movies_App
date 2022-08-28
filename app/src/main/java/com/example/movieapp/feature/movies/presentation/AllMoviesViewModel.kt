package com.example.movieapp.feature.movies.presentation

import androidx.paging.PagingData
import androidx.paging.map
import com.example.base.view.BaseViewModel
import com.example.movieapp.R
import com.example.movieapp.feature.movies.domain.GetPopularMoviesUseCase
import com.example.movieapp.feature.movies.domain.GetTopRatedMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class AllMoviesViewModel(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val mapper: AllMoviesMapper
) : BaseViewModel<AllMoviesState>(AllMoviesState.Loading) {

    val sortChoiceStateFlow = MutableStateFlow(R.id.menu_sort_by_most_popular)

    @OptIn(ExperimentalCoroutinesApi::class)
    val moviesPagingFlow: Flow<PagingData<MovieUiModel>> = sortChoiceStateFlow
        .flatMapLatest {
            when (it) {
                R.id.menu_sort_by_top_rated -> {
                    getTopRatedMoviesUseCase()
                }
                else -> {
                    getPopularMoviesUseCase()
                }
            }.map { pagingData ->
                pagingData.map { movieDto ->
                    mapper.toMovieUiModel(movieDto)
                }
            }
        }


    fun onNewPageSuccess() {
        updateState(AllMoviesState.Success)
    }

    fun onInitialPagingLoading() {
        updateState(AllMoviesState.Loading)
    }

    fun onInitialPagingEmpty() {
        updateState(AllMoviesState.Empty)
    }

    fun onInitialPagingError(it: Throwable) {
        updateState(AllMoviesState.Error(it))
    }

    fun sortByMostPopular() {
        sortChoiceStateFlow.value = R.id.menu_sort_by_most_popular
    }

    fun sortByTopRated() {
        sortChoiceStateFlow.value = R.id.menu_sort_by_top_rated
    }

}