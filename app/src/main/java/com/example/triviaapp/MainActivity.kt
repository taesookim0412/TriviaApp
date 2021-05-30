package com.example.triviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.triviaapp.api.TriviaViewModel
import com.example.triviaapp.fragments.AllTrivias
import com.example.triviaapp.schemas.Trivia.TriviaPointsDatabase
import com.example.triviaapp.schemas.Trivia.TriviaPointsRepository

class MainActivity : AppCompatActivity() {
    val triviaViewModel:TriviaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriviaPointsDatabase.getInstance(applicationContext)
        TriviaPointsDatabase.getTriviaPointsDao(applicationContext)
        triviaViewModel.getCategoriesFromAPI.observe(this) {
            it?.let { it1 -> TriviaPointsRepository().populateOrVerifyDb(it1) };
        }

        setContentView(R.layout.activity_main)
        loadFragment(AllTrivias())

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}