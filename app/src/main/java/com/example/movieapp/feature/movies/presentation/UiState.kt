package com.example.movieapp.feature.movies.presentation

import android.os.Parcelable
import com.example.base.view.BaseEntity
import com.example.base.view.UiState
import kotlinx.parcelize.Parcelize


sealed class AllMoviesState : UiState {
    object Loading : AllMoviesState()

    data class Error(val exception: Throwable) : AllMoviesState()

    object Empty : AllMoviesState()

    object Success : AllMoviesState()
}

@Parcelize
data class MovieUiModel(
    val id: String,
    val title: String?,
    val voteAvg: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val originalTitle: String?,
    val overview: String?,
    val releaseDate: String?,
) : BaseEntity, Parcelable {
    override fun entityId() = id
}