package com.example.movieapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(
    value = ["fileUrl"],
    requireAll = false
)
fun loadFileImage(
    imageView: ImageView,
    url: String?
) {
    Glide.with(imageView).load(url).into(imageView)
}