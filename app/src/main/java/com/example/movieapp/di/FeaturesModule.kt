package com.example.movieapp.di

import com.example.movieapp.feature.detailsMovie.di.detailsModule
import com.example.movieapp.feature.movies.di.moviesModule


val allFeaturesModules = listOf(
    moviesModule,
    detailsModule
)