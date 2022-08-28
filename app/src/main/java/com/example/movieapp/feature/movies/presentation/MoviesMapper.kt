package com.example.movieapp.feature.movies.presentation

import com.example.base.utils.Constants.BACKDROP_BASE_URL
import com.example.base.utils.Constants.POSTER_BASE_URL
import com.example.data.network.models.MovieDto

class MoviesMapper {

    fun toMovieUiModel(movieDto: MovieDto): MovieUiModel {
        return MovieUiModel(
            id = movieDto.id.toString(),
            title = movieDto.title,
            voteAvg = movieDto.voteAverage.toString(),
            posterUrl = buildPosterUrl(movieDto.posterPath),
            backdropUrl = buildBackdropUrl(movieDto.backdropPath),
            originalTitle = movieDto.originalTitle,
            overview = movieDto.overview,
            releaseDate = movieDto.releaseDate
        )
    }

    fun buildPosterUrl(posterPath: String?): String {
        return POSTER_BASE_URL + posterPath
    }

    fun buildBackdropUrl(backdropPath: String?): String {
        return BACKDROP_BASE_URL + backdropPath
    }

}