package com.example.movieapp.feature.detailsMovie.presentation

import com.example.base.view.BaseViewModel
import com.example.movieapp.feature.movies.presentation.MovieUiModel

class DetailsViewModel : BaseViewModel<DetailsState>(DetailsState()) {

    fun setArgsData(movieUiModel: MovieUiModel?) {
        updateState(currentState.copy(movieUiModel = movieUiModel))
    }


}