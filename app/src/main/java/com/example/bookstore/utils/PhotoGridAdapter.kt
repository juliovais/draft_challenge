package com.example.bookstore.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.databinding.FragmentVolumeItemBinding

class PhotoGridAdapter :
    ListAdapter<CoverImage, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback) {

    class MarsPhotosViewHolder(
        private var binding: FragmentVolumeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto: CoverImage) {
            binding.photo = marsPhoto
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CoverImage>() {
        override fun areItemsTheSame(oldItem: CoverImage, newItem: CoverImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoverImage, newItem: CoverImage): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPhotosViewHolder {
        return MarsPhotosViewHolder(
            FragmentVolumeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}
