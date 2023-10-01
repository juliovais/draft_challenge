package com.example.bookstore.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bookstore.core.retrofit.CoverImage

@BindingAdapter("coverList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CoverImage>?) {
    val adapter = recyclerView.adapter as VolumeListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri)
    }
}
