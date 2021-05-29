package com.example.triviaapp.api

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.triviaapp.api.base.BaseAPIRepository
import retrofit2.Response
import retrofit2.Retrofit

class TriviaAPIRepository():  BaseAPIRepository() {
    /*private val api: RestAPI = RestAPI()
    private val retroFitInst: Retrofit = api.retrofit*/
    val apiService: TriviaApiService = RestAPI().triviaApi

    suspend fun getCategories() = attemptQueryOrCancel({apiService.getCategories()})

    suspend fun getQuestion() = attemptQueryOrCancel({apiService.getQuestion()})

    suspend fun getApiToken() = attemptQueryOrCancel({apiService.getApiToken()})

    //empty param = random
    suspend fun get50CustomQuestions(category:String, difficulty: String, type: String): Array<TriviaResultsResponse>? /* :List<TriviaDataResponse>?*/
    {
        Log.d("Here", "Here")
        val response:TriviaDataResponse?  = attemptQueryOrCancel({apiService.get50CustomQuestions(category, difficulty, type)})
        val responseResults = parseResults(response)

        return responseResults

        //Retrieved Array<T> now place into Schema

    }

    //helper method
    suspend fun parseResults(response: TriviaDataResponse?): Array<TriviaResultsResponse>?{
        Log.d("Parsing", "Parsing")
        return response?.results
    }
}