package com.example.data.repository.movies

import com.example.base.data.BaseRemoteDataSource
import com.example.data.network.api.MoviesApi
import com.example.data.network.models.MovieDto

class MoviesRemoteDataSourceRetrofitImpl(
    private val moviesApi: MoviesApi
) : BaseRemoteDataSource(), MoviesRemoteDataSource {

    override suspend fun getPopularMovies(page: Int): List<MovieDto>? {
        return makeRequest {
            moviesApi.getPopularMovies(page)
        }.movies
    }

    override suspend fun getTopRatedMovies(page: Int): List<MovieDto>? {
        return makeRequest {
            moviesApi.getTopRatedMovies(page)
        }.movies
    }
}