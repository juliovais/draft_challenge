package com.example.bookstore.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookstore.application.fragments.ListVolumesFragmentDirections
import com.example.bookstore.core.retrofit.CoverImage
import com.example.bookstore.databinding.FragmentVolumeItemBinding

class VolumeListAdapter :
    ListAdapter<CoverImage, VolumeListAdapter.VolumeListViewHolder>(DiffCallback) {

    class VolumeListViewHolder(
        var binding: FragmentVolumeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coverImage: CoverImage) {
            binding.cover = coverImage
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CoverImage>() {
        override fun areItemsTheSame(oldItem: CoverImage, newItem: CoverImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoverImage, newItem: CoverImage): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VolumeListViewHolder {
        val viewHolder = VolumeListViewHolder(
            FragmentVolumeItemBinding.inflate(LayoutInflater.from(parent.context))
        )

        viewHolder.itemView.setOnClickListener {

            val action =
                ListVolumesFragmentDirections.actionListVolumesFragmentToVolumeDetailFragment(
                    viewHolder.binding.cover?.id!!
                )

            findNavController(parent).navigate(action)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: VolumeListViewHolder, position: Int) {
        val coverImage = getItem(position)
        holder.bind(coverImage)
    }
}
