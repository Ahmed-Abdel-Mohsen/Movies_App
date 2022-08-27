package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class MoviesPaginatedResponse(
    @SerializedName("results")
    val movies: List<MovieDto>?
)

data class MovieDto(
    val adult: Boolean,
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)