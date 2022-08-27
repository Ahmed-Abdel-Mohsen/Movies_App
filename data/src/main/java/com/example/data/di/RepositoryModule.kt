package com.example.data.di

import com.example.data.repository.movies.*
import org.koin.dsl.module


/**
 * Koin module for repositories & remote/local data sources.
 */
val repositoryModule = module {

    //Movies Repo
    factory<MoviesRemoteDataSource> { MoviesRemoteDataSourceRetrofitImpl(get()) }
    factory<MoviesRepo> { MoviesRepoImpl(get(), get()) }
    factory { MoviesPagingSource(get()) }

}