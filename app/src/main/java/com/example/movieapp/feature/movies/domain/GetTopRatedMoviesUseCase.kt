package com.example.movieapp.feature.movies.domain

import androidx.paging.Pager
import com.example.base.data.BasePagingSource.Companion.getDefaultPagingConfig
import com.example.data.repository.movies.TopRatedMoviesPagingSource

class GetTopRatedMoviesUseCase(
    private val topRatedMoviesPagingSource: TopRatedMoviesPagingSource
) {
    operator fun invoke() = Pager(getDefaultPagingConfig()) {
        topRatedMoviesPagingSource
    }.flow
}