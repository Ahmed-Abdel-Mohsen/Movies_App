package com.example.movieapp.feature.movies.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.base.view.BaseViewModel
import com.example.movieapp.feature.movies.domain.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class AllMoviesViewModel(
    val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val mapper: AllMoviesMapper
) : BaseViewModel<AllMoviesState>(AllMoviesState.Loading) {

    var moviesPagingFlow: Flow<PagingData<MovieUiModel>> = getPopularMoviesUseCase().map {
        it.map { movieDto ->
            mapper.toMovieUiModel(movieDto)
        }
    }.cachedIn(viewModelScope)

    fun onNewPageSuccess() {
        getPopularMoviesUseCase().onEach {

        }
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

}