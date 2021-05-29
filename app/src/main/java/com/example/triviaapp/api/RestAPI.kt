package com.example.triviaapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPI {
    var retrofit:Retrofit
    var triviaApi: TriviaApiService

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
//            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
        triviaApi = retrofit.create(TriviaApiService::class.java)

    }

    private fun authInterceptor(token:String) = Interceptor{ chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("token", token)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)

    }

    private fun networkInterceptor(){

    }

    private fun switchToken_NewClient(token: String): OkHttpClient{
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor(token))
            .build()
    }

    fun switchToken_NewBuild(token:String){
        retrofit = retrofit.newBuilder()
            .client(switchToken_NewClient(token))
            .build()
        triviaApi = retrofit.create(TriviaApiService::class.java)
    }
}