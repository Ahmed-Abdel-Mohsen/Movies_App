package com.example.movieapp.feature.movies.view

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.base.utils.makeGone
import com.example.base.utils.makeVisible
import com.example.base.utils.onStates
import com.example.base.view.BaseActivity
import com.example.base.view.LoaderStateAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMoviesBinding
import com.example.movieapp.feature.detailsMovie.view.DetailsActivity
import com.example.movieapp.feature.movies.presentation.AllMoviesState
import com.example.movieapp.feature.movies.presentation.MovieUiModel
import com.example.movieapp.feature.movies.presentation.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MoviesActivity : BaseActivity<ActivityMoviesBinding, AllMoviesState, MoviesViewModel>() {

    private val moviesAdapter = MoviePagedListAdapter(::openDetailsActivity)

    private val loadStateAdapter = LoaderStateAdapter { moviesAdapter.retry() }

    override fun getLayoutRes() = R.layout.activity_movies

    override fun getVM(): MoviesViewModel = getViewModel()

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

    private fun initRecyclerView() {
        binding.moviesRecyclerView.apply {
            adapter = moviesAdapter.withLoadStateFooter(loadStateAdapter)
            layoutManager = GridLayoutManager(this@MoviesActivity, 2)
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

    private fun openDetailsActivity(movieUiModel: MovieUiModel) {
        val intent = DetailsActivity.createIntent(this, movieUiModel)

        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        setSelectedSortOption(viewModel.sortChoiceStateFlow.value, menu)
        return true
    }

    private fun setSelectedSortOption(@IdRes id: Int, menu: Menu) {
        when (id) {
            R.id.menu_sort_by_most_popular -> {
                menu.getItem(0).isChecked = true
            }
            R.id.menu_sort_by_top_rated -> {
                menu.getItem(1).isChecked = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort_by_most_popular -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    viewModel.sortByMostPopular()
                }
            }
            R.id.menu_sort_by_top_rated -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    viewModel.sortByTopRated()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
