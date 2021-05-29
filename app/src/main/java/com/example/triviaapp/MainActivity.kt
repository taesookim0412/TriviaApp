package com.example.triviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.triviaapp.schemas.Trivia.TriviaPointsRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriviaPointsDatabase.getInstance(applicationContext)
        TriviaPointsDatabase.getTriviaPointsDao(applicationContext)
        TriviaPointsRepository().populateOrVerifyDb(arrayOf("Category1", "Category2", "Category3"))
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