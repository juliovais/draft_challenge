package com.example.bookstore.utils

import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstore.ListVolumesFragment
import com.example.bookstore.ListVolumesFragmentDirections
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.databinding.FragmentVolumeItemBinding

class PhotoGridAdapter :
    ListAdapter<CoverImage, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback) {

    class MarsPhotosViewHolder(
        var binding: FragmentVolumeItemBinding
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
        val viewHolder = MarsPhotosViewHolder(
            FragmentVolumeItemBinding.inflate(LayoutInflater.from(parent.context))
        )

        viewHolder.itemView.setOnClickListener {

            val action = ListVolumesFragmentDirections.actionListVolumesFragmentToVolumeDetailFragment()
            //val action = ListVolumesFragmentDirections.actionListVolumesFragmentToVolumeDetailFragment(viewHolder.binding.photo.id.toInt())
            findNavController(parent).navigate(action)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }
}
