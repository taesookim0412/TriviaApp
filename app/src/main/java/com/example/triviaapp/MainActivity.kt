package com.example.triviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triviaapp.schemas.Trivia.TriviaPointsDatabase
import com.example.triviaapp.schemas.Trivia.TriviaPointsRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriviaPointsDatabase.getInstance(applicationContext)
        TriviaPointsDatabase.getTriviaPointsDao(applicationContext)
        setContentView(R.layout.activity_main)
        TriviaPointsRepository().populateOrVerifyDb(arrayOf("Category1", "Category2", "Category3"))
    }
}