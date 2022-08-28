package com.example.base.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter(
    value = ["fileUrl"],
    requireAll = false
)
fun loadFileImage(
    imageView: ImageView,
    url: String?
) {
    Picasso.get().load(url).into(imageView)
}