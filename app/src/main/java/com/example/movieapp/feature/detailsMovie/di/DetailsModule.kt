package com.example.movieapp.feature.detailsMovie.di

import com.example.movieapp.feature.detailsMovie.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val detailsModule = module {

    viewModelOf(::DetailsViewModel)

}