package com.example.bookstore.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.databinding.FragmentVolumeDetailBinding

class VolumeDetailFragment : Fragment() {

    private val args: VolumeDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentVolumeDetailBinding
    private val bookViewModel by activityViewModels<BookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVolumeDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = bookViewModel

        bookViewModel.getVolume(args.idVolume)

        return binding.root
    }
}