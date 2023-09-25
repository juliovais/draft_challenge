package com.example.bookstore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.bookstore.utils.PhotoGridAdapter
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.databinding.FragmentListVolumesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListVolumesFragment : Fragment() {

    private lateinit var binding: FragmentListVolumesBinding
    private val bookViewModel by viewModels<BookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListVolumesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = bookViewModel

        binding.buttonTest.setOnClickListener {

            bookViewModel.test()
        }

        binding.photosGrid.adapter = PhotoGridAdapter()

        return binding.root
    }
}