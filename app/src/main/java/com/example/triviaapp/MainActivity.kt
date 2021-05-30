package com.example.triviaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.triviaapp.api.TriviaViewModel
import com.example.triviaapp.fragments.AllCategories
import com.example.triviaapp.fragments.AllTrivias
import com.example.triviaapp.schemas.Trivia.TriviaPointsDatabase
import com.example.triviaapp.schemas.Trivia.TriviaPointsRepository
import com.example.triviaapp.viewModels.CategoryEntityViewModel
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class MainActivity : AppCompatActivity() {
    // initialized as activity view models!!
    private val triviaViewModel:TriviaViewModel by viewModels()
    private val triviaEntityViewModel: TriviaEntityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriviaPointsDatabase.getInstance(applicationContext)
        TriviaPointsDatabase.getTriviaPointsDao(applicationContext)
        if (triviaViewModel.initial_state){
            triviaViewModel.getCategoriesFromAPI.observe(this) {
                it?.let { it1 -> TriviaPointsRepository().populateOrVerifyDb(this@MainActivity, it1) };
            }
        }
        setContentView(R.layout.activity_main)

    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
        triviaViewModel.initial_state = false
    }
}