package com.example.movieapp.feature.movies.domain

import androidx.paging.Pager
import com.example.base.data.BasePagingSource.Companion.getDefaultPagingConfig
import com.example.data.repository.movies.MostPopularMoviesPagingSource

class GetPopularMoviesUseCase(
    private val mostPopularMoviesPagingSource: MostPopularMoviesPagingSource
) {
    operator fun invoke() = Pager(getDefaultPagingConfig()) {
        mostPopularMoviesPagingSource
    }.flow
}