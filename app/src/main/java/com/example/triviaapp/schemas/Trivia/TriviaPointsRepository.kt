package com.example.triviaapp.schemas.Trivia

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TriviaPointsRepository {
    private val triviaPointsDao:TriviaPointsDao = TriviaPointsDatabase.triviaPointsDao;

    init{

    }

    fun populateOrVerifyDb(apiCategories: Array<String>){
        CoroutineScope(Dispatchers.IO).launch{
            withContext(Dispatchers.IO){
                val categoriesFromDb = triviaPointsDao.findAll()
                val categoriesFromDbSet:MutableSet<String> = mutableSetOf()
                //Create a mutable set of categories in the room database
                for (categoryFromDb in categoriesFromDb){
                    categoryFromDb.category?.let { categoriesFromDbSet.add(it) }
                }
                // verify every api category has a database entity
                for (apiCategory in apiCategories){
                    if (!(categoriesFromDbSet.contains(apiCategory))){
                        triviaPointsDao.createTriviaPoints(TriviaPoints(apiCategory))
                    }
                }
            }
        }

    }
}