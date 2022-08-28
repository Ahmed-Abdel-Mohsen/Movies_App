package com.example.movieapp.feature.detailsMovie.view

import android.content.Context
import android.content.Intent
import com.example.base.view.BaseActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityDetailsBinding
import com.example.movieapp.feature.detailsMovie.presentation.DetailsState
import com.example.movieapp.feature.detailsMovie.presentation.DetailsViewModel
import com.example.movieapp.feature.movies.presentation.MovieUiModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsState, DetailsViewModel>() {

    override fun getLayoutRes() = R.layout.activity_details

    override fun getVM(): DetailsViewModel = getViewModel()

    override fun renderState(uiState: DetailsState) {
        uiState.movieUiModel?.let {
            binding.movieUiModel = it
        }
    }

    override fun getToolbarTitle(): Any? = null

    override fun onViewAttach() {
        super.onViewAttach()
        readIntentExtras()
    }

    private fun readIntentExtras() {
        viewModel.setArgsData(
            intent.getParcelableExtra(EXTRA_MOVIE)
        )
    }

    companion object {

        private const val EXTRA_MOVIE = "EXTRA_MOVIE"

        fun createIntent(context: Context, movieUiModel: MovieUiModel): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movieUiModel)
            }
        }
    }
}