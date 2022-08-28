package com.example.data.repository.movies

import com.example.base.data.Resource
import com.example.data.network.models.MovieDto
import kotlinx.coroutines.flow.Flow


interface MoviesRemoteDataSource {
    suspend fun getPopularMovies(page: Int): List<MovieDto>?

    suspend fun getTopRatedMovies(page: Int): List<MovieDto>?
}

interface MoviesRepo {
    fun getPopularMovies(page: Int): Flow<Resource<List<MovieDto>?>>
    fun getTopRatedMovies(page: Int): Flow<Resource<List<MovieDto>?>>
}