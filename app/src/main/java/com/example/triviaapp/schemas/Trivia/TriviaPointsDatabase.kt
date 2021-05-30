package com.example.triviaapp.schemas.Trivia

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TriviaPoints::class], version = 1, exportSchema = false)
abstract class TriviaPointsDatabase : RoomDatabase() {
    abstract fun triviaPointsDao(): TriviaPointsDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: TriviaPointsDatabase? = null

        @Volatile
        lateinit var triviaPointsDao: TriviaPointsDao

        fun getTriviaPointsDao(context:Context): TriviaPointsDao{
            return if (this::triviaPointsDao.isInitialized) triviaPointsDao else getInstance(context).triviaPointsDao().also { triviaPointsDao = it }
        }

        fun getInstance(context: Context): TriviaPointsDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): TriviaPointsDatabase {
            return Room.databaseBuilder(context, TriviaPointsDatabase::class.java, "triviapoints-db")
//                .addMigrations(AlarmMigrations.MIGRATION_1_2)\
                .build()
        }
    }
}