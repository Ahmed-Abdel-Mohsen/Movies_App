package com.example.movieapp.feature.movies.di

import com.example.movieapp.feature.movies.domain.GetPopularMoviesUseCase
import com.example.movieapp.feature.movies.domain.GetTopRatedMoviesUseCase
import com.example.movieapp.feature.movies.presentation.AllMoviesMapper
import com.example.movieapp.feature.movies.presentation.AllMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val moviesModule = module {

    factoryOf(::AllMoviesMapper)
    factoryOf(::GetTopRatedMoviesUseCase)
    factoryOf(::GetPopularMoviesUseCase)

    viewModelOf(::AllMoviesViewModel)

}