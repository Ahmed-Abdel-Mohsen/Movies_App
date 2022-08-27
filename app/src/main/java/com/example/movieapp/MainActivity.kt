package com.example.movieapp

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.utils.makeGone
import com.example.base.utils.makeVisible
import com.example.base.utils.onStates
import com.example.base.view.BaseActivity
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.feature.movies.presentation.AllMoviesState
import com.example.movieapp.feature.movies.presentation.AllMoviesViewModel
import com.example.movieapp.feature.movies.view.MoviePagedListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<ActivityMainBinding, AllMoviesState, AllMoviesViewModel>() {

    private val moviesAdapter = MoviePagedListAdapter()

    override fun getLayoutRes() = R.layout.activity_main

    override fun getVM(): AllMoviesViewModel = getViewModel()

    override fun renderState(uiState: AllMoviesState) {
        when (uiState) {
            is AllMoviesState.Loading -> {
                onLoadingState()
            }
            is AllMoviesState.Error -> {
                onErrorState()
            }
            is AllMoviesState.Success -> {
                onSuccessState()
            }
            is AllMoviesState.Empty -> {
                onEmptyState()
            }
        }
    }

    private fun onLoadingState() {
        binding.progressBar.makeVisible()
    }

    private fun onErrorState() {
        binding.genericTextView.text = getString(R.string.error_occurred_please_try_again_later)
        binding.genericTextView.makeVisible()
    }

    private fun onSuccessState() {
        binding.progressBar.makeGone()
    }

    private fun onEmptyState() {
        binding.genericTextView.text = getString(R.string.no_data_please_try_again_later)
        binding.genericTextView.makeVisible()
    }

    override fun getToolbarTitle(): Any? = null

    override fun onViewAttach() {
        setUpViews()
        initRecyclerView()
    }

    private fun setUpViews() {
    }

    fun initRecyclerView() {
        binding.moviesRecyclerView.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }

        lifecycleScope.launch {
            viewModel.moviesPagingFlow.distinctUntilChanged()
                .collectLatest {
                    moviesAdapter.submitData(it)
                }
        }

        moviesAdapter.onStates(
            onInitialLoading = {
                viewModel.onInitialPagingLoading()

            },
            onInitialEmpty = {
                viewModel.onInitialPagingEmpty()

            },
            onInitialError = {
                viewModel.onInitialPagingError(it)
            },
            onNewPageSuccess = {
                viewModel.onNewPageSuccess()
            }
        )

    }
}