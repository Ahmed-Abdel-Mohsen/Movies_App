package com.example.movieapp.feature.movies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.base.view.BasePagedListAdapter
import com.example.movieapp.databinding.MovieListItemBinding
import com.example.movieapp.feature.movies.presentation.MovieUiModel


class MoviePagedListAdapter(private val onMovieClick: (movie: MovieUiModel) -> Unit) :
    BasePagedListAdapter<MovieUiModel>() {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieListItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun bind(binding: ViewDataBinding, item: MovieUiModel?) {
        with(binding as MovieListItemBinding) {
            movie = item

            binding.root.setOnClickListener {
                item?.let(onMovieClick)
            }

            //just to make the marquee effect works
            tvMovieTitle.isSelected = true
        }
    }


}