package com.example.bookstore.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bookstore.utils.VolumeListAdapter
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.databinding.FragmentListVolumesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListVolumesFragment : Fragment() {

    private lateinit var binding: FragmentListVolumesBinding
    private val bookViewModel by activityViewModels<BookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListVolumesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = bookViewModel

        bookViewModel.loadData()

        binding.coverList.adapter = VolumeListAdapter()

        return binding.root
    }
}