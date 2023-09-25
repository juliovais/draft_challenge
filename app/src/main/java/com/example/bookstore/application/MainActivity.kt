package com.example.bookstore.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bookstore.R
import com.example.bookstore.application.viewmodels.BookViewModel
import com.example.bookstore.core.SettingsDataStore
import com.example.bookstore.repositories.retrofit.BookRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

private val bookViewModel by viewModels<BookViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myButton = findViewById<Button>(R.id.my_button)
        val image = findViewById<ImageView>(R.id.image_view_test)

        myButton.setOnClickListener {

            val dataStore = SettingsDataStore()

            lifecycleScope.launch {

                val DBCreated = dataStore.readBooleanValue(applicationContext, "test")

                if (!DBCreated) {

                    println("if")

                    bookViewModel.test()

                    dataStore.saveBooleanValue(applicationContext, "test", true)
                } else {

                    println("else")
                }
            }




        }
    }
}