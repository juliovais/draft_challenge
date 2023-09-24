package com.example.bookstore.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.bookstore.R
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.repositories.retrofit.BookRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

private val bookViewModel by viewModels<BookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Button>(R.id.my_button)
        val image = findViewById<ImageView>(R.id.image_view_test)

        myButton.setOnClickListener {

            bookViewModel.test(image)
        }
    }
}