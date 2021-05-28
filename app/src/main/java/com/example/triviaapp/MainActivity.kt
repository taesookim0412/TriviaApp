package com.example.triviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.triviaapp.api.TriviaDataResponse
import com.example.triviaapp.api.TriviaViewModel
import kotlinx.coroutines.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    val triviaViewModel:TriviaViewModel by viewModels();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        triviaViewModel.oneQuestion.observe(this) { data -> Log.d("logdata", data.toString()) }

    }
}