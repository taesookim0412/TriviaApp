package com.example.triviaapp.api.base
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.coroutineContext

open class BaseAPIRepository {

    suspend fun <T: Any> attemptQueryOrCancel(apiQuery: suspend () -> Response<T>): T?{
        val result = attemptQuery(apiQuery)
        var data:T? = null
        when (result){
            is Result.Success ->
                data = result.data
            is Result.Error ->
            { Log.d("Cancelling", "cancelling")
                coroutineContext.cancel() }
        }
        return data
    }

    suspend fun <T : Any> attemptQuery(apiQuery: suspend () -> Response<T>, ct: Int = 0): Result<T> {
        //Wait 10 seconds if failed query
        Log.d("Retrieving", "Rerieving")

        if (ct > 0) delay(10000)
        var response:Response<T>
        try{
            response = apiQuery.invoke()
        }
        catch(e: Exception){
            //create an empty T
            Log.d("Exception:", e.toString())
            return Result.Error(e)
        }
        val resultCode = response.code()
        Log.d(resultCode.toString(), response.isSuccessful.toString())
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        }
        else {
            Log.d("ct", ct.toString())
            if (ct == 3) {
                return Result.Error(IOException("Timeout"))
            }
            return attemptQuery(apiQuery,ct + 1)
        }
    }

}