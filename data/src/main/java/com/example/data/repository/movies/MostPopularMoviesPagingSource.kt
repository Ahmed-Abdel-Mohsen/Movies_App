package com.example.data.repository.movies


class MostPopularMoviesPagingSource(
    private val moviesRepo: MoviesRepo
) : BaseMoviesPagingSource() {
    override fun getDesiredMovies(currentLoadingPageKey: Int) =
        moviesRepo.getPopularMovies(currentLoadingPageKey)
}