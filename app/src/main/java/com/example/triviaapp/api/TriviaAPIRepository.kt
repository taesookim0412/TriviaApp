package com.example.triviaapp.api

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.triviaapp.api.base.BaseAPIRepository
import com.example.triviaapp.utils.DecodeHtml
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
    suspend fun get50CustomQuestions(category:String, difficulty: String, type: String): MutableList<TriviaResultsResponse>? /* :List<TriviaDataResponse>?*/
    {
        val response:TriviaDataResponse?  = attemptQueryOrCancel({apiService.get50CustomQuestions(category, difficulty, type)})
        val responseResults = parseResults(response)

        return responseResults

        //Retrieved Array<T> now place into Schema

    }

    //helper method
    //cleanse
    suspend fun parseResults(response: TriviaDataResponse?): MutableList<TriviaResultsResponse>?{
        var res = mutableListOf<TriviaResultsResponse>()

        for (triviaResponse in response?.results!!){
            val cleansed_incorrect_answers = triviaResponse.incorrect_answers.map { incorrect_answer -> DecodeHtml.decode(incorrect_answer) }.toTypedArray()
            val cleansedResponse = TriviaResultsResponse(
                DecodeHtml.decode(triviaResponse.category),
                DecodeHtml.decode(triviaResponse.type),
                DecodeHtml.decode(triviaResponse.difficulty),
                DecodeHtml.decode(triviaResponse.question),
                DecodeHtml.decode(triviaResponse.correct_answer),
                cleansed_incorrect_answers)
            res.add(cleansedResponse)
        }
        return res
    }
}