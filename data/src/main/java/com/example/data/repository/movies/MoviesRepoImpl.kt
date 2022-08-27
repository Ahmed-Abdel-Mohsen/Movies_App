package com.example.data.repository.movies

import com.example.base.data.BaseRepo
import com.example.base.data.Resource
import com.example.base.utils.NetworkConnectivityHelper
import com.example.data.network.models.MovieDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class MoviesRepoImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    networkConnectivityHelper: NetworkConnectivityHelper,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepo(networkConnectivityHelper, ioDispatcher), MoviesRepo {

    override fun getPopularMovies(page: Int): Flow<Resource<List<MovieDto>?>> {
        return networkOnlyFlow {
            moviesRemoteDataSource.getPopularMovies(page)
        }
    }

    override fun getTopRatedMovies(page: Int): Flow<Resource<List<MovieDto>?>> {
        return networkOnlyFlow {
            moviesRemoteDataSource.getTopRatedMovies(page)
        }
    }

}