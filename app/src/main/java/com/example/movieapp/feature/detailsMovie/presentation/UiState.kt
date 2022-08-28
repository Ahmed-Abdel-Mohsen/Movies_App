package com.example.movieapp.feature.detailsMovie.presentation

import com.example.base.view.UiState
import com.example.movieapp.feature.movies.presentation.MovieUiModel

data class DetailsState(
    val movieUiModel: MovieUiModel? = null
) : UiState