package com.example.bookstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.core.room.BookRoomDatabase
import com.example.bookstore.databinding.FragmentListVolumesBinding
import com.example.bookstore.databinding.FragmentVolumeDetailBinding
import kotlinx.coroutines.launch

class VolumeDetailFragment : Fragment() {

    val args: VolumeDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentVolumeDetailBinding
    private val bookViewModel by activityViewModels<BookViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentVolumeDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = bookViewModel

        bookViewModel.getVolume(args.idVolume.toInt())

        return binding.root
    }
}