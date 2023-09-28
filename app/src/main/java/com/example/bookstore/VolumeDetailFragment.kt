package com.example.bookstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.databinding.FragmentListVolumesBinding
import com.example.bookstore.databinding.FragmentVolumeDetailBinding
import kotlinx.coroutines.launch

class VolumeDetailFragment : Fragment() {

    private lateinit var binding: FragmentVolumeDetailBinding
    private val bookViewModel by activityViewModels<BookViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentVolumeDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = bookViewModel

        lifecycleScope.launch {
            val volume = bookViewModel.getVolume(10)

            println(volume.id)
            println(volume.title)
            println(volume.authors)
            println(volume.description)
            println(volume.thumbnail)
            println(volume.buyLink)
            println(volume.favorite)
        }



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volume_detail, container, false)
    }
}