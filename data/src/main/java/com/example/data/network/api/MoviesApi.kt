package com.example.data.network.api

import com.example.data.network.models.MoviesPaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviesPaginatedResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Response<MoviesPaginatedResponse>

}