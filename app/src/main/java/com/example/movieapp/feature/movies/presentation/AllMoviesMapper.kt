package com.example.movieapp.feature.movies.presentation

import com.example.base.utils.Constants.IMAGES_BASE_URL
import com.example.data.network.models.MovieDto

class AllMoviesMapper {

    fun toMovieUiModel(movieDto: MovieDto): MovieUiModel {
        return MovieUiModel(
            movieDto.id.toString(),
            movieDto.title,
            movieDto.voteAverage.toString(),
            buildImageUrl(movieDto.posterPath)
        )
    }

    fun buildImageUrl(posterPath: String): String {
        return IMAGES_BASE_URL + posterPath
    }

}