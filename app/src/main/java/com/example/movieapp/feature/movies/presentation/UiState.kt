package com.example.movieapp.feature.movies.presentation

import com.example.base.view.BaseEntity
import com.example.base.view.UiState


sealed class AllMoviesState : UiState {
    object Loading : AllMoviesState()

    data class Error(val exception: Throwable) : AllMoviesState()

    object Empty : AllMoviesState()

    object Success : AllMoviesState()
}

data class MovieUiModel(
    val id: String,
    val title: String,
    val voteAvg: String,
    val photoUrl: String
) : BaseEntity {
    override fun entityId() = id
}