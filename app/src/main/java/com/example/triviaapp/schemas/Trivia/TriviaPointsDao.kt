package com.example.triviaapp.schemas.Trivia

import androidx.room.*

@Dao
interface TriviaPointsDao {
    @Insert
    suspend fun createTriviaPoints(triviaPoints: TriviaPoints)

    @Update
    suspend fun updateTriviaPoints(triviaPoints: TriviaPoints)

    @Delete
    suspend fun deleteTriviaPoints(triviaPoints: TriviaPoints)

    @Query("DELETE FROM triviapoints WHERE apiId = :apiId")
    suspend fun deleteTriviaPointByApiId(apiId:Long?)

    @Query("SELECT * from triviapoints")
    suspend fun findAll(): List<TriviaPoints>

    @Query("SELECT * from triviapoints WHERE id = :id LIMIT 1")
    suspend fun findById(id:Long?): TriviaPoints

    @Query("SELECT * from triviapoints WHERE category = :category LIMIT 1")
    suspend fun findByCategory(category:String?): TriviaPoints

}