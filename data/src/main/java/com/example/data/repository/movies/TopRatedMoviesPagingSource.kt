package com.example.data.repository.movies

class TopRatedMoviesPagingSource(
    private val moviesRepo: MoviesRepo
) : BaseMoviesPagingSource() {
    override fun getDesiredMovies(currentLoadingPageKey: Int) =
        moviesRepo.getTopRatedMovies(currentLoadingPageKey)
}