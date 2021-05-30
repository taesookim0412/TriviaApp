package com.example.triviaapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.triviaapp.api.TriviaResultsResponse

class CategoryEntityViewModel(val apiIdLong:Long?, val category:String?, val pointsLong:Long? ): ViewModel() {
    val apiIdStr = apiIdLong.toString()
    val pointsStr = pointsLong.toString()
}