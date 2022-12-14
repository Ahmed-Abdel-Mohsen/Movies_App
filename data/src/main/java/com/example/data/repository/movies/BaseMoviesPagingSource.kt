package com.example.data.repository.movies

import com.example.base.data.BasePagingSource
import com.example.base.data.Resource
import com.example.data.network.models.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

abstract class BaseMoviesPagingSource : BasePagingSource<MovieDto>() {

    override suspend fun getData(currentLoadingPageKey: Int): List<MovieDto>? {

        val paginatedMoviesResource =
            getDesiredMovies(currentLoadingPageKey).firstOrNull {
                it is Resource.Success || it is Resource.Error
            }

        return when (paginatedMoviesResource) {
            is Resource.Success -> paginatedMoviesResource.data
            is Resource.Error -> throw paginatedMoviesResource.exception
            else -> error("The resource returned should be either Success or Error")
        }
    }

    abstract fun getDesiredMovies(currentLoadingPageKey: Int): Flow<Resource<List<MovieDto>?>>

}