package com.example.triviaapp.api

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Parent class
data class TriviaDataResponse(
    val response_code: Int,
    val results: Array<TriviaResultsResponse>
)

//Results array
data class TriviaResultsResponse(
    val category:String,
    val type:String,
    val difficulty:String,
    val question:String,
    val correct_answer:String,
    val incorrect_answers: Array<String>
)

//API response
data class TokenDataResponse(
    val response_code: Int,
    val response_message: String,
    val token: String
)

// Parent Category Response
data class TriviaCategoryResponse(
    val trivia_categories: Array<TriviaCategoryResultsResponse>
)

//Category Array Results Response
data class TriviaCategoryResultsResponse(
    val id: String,
    val name: String
)


interface TriviaApiService {
    @GET("api.php?amount=1")
    suspend fun getQuestion(): Response<TriviaDataResponse>

    @GET("api.php?amount=50")
    suspend fun get50CustomQuestions(@Query("category") category: String, @Query("difficulty") difficulty: String, @Query("type") type: String) : Response<TriviaDataResponse>

    @GET("api_category.php")
    suspend fun getCategories(): Response<TriviaCategoryResponse>

    @GET("api_token.php?command=request")
    suspend fun getApiToken(): Response<TokenDataResponse>


}
/*

data class TriviaQuestionsResponse(
        val results:List<TriviaDataResponse>
)*/
