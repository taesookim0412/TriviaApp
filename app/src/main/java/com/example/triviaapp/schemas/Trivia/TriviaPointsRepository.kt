package com.example.triviaapp.schemas.Trivia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.liveData
import com.example.triviaapp.MainActivity
import com.example.triviaapp.R
import com.example.triviaapp.api.TriviaCategoryResultsResponse
import com.example.triviaapp.fragments.AllCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TriviaPointsRepository {
    val triviaPointsDao: TriviaPointsDao = TriviaPointsDatabase.triviaPointsDao

    init {

    }

    fun populateOrVerifyDb(mainActivity: MainActivity, apiCategories: Array<TriviaCategoryResultsResponse>) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val categoriesFromDb = triviaPointsDao.findAll()
                val categoriesFromDbMap: MutableMap<Long, String> = mutableMapOf()
                //Create a mutable map of apiId to category string in the room database
                for (categoryFromDb in categoriesFromDb) {
                    categoryFromDb.let {
                        it.apiId?.let { it1 ->
                            it.category?.let { it2 ->
                                categoriesFromDbMap.put(it1, it2)
                            }
                        }
                    }
                }
                // verify every api category has a database entity
                for (apiCategory in apiCategories) {
                    val apiCategoryIdToLong = apiCategory.id.toLong()
                    val dbStrForApiId = categoriesFromDbMap.get(apiCategoryIdToLong)
                    // if db has no entity with api id
                    if (dbStrForApiId == null) {
                        triviaPointsDao.createTriviaPoints(
                            TriviaPoints(
                                apiCategoryIdToLong,
                                apiCategory.name
                            )
                        )
                    }
                    // else if there is a collision : the lookup by api id gave inconsistent string results.
                    else if (dbStrForApiId != apiCategory.name) {
                        //delete the db entity by api Id and recreate it,,,
                        triviaPointsDao.deleteTriviaPointByApiId(apiCategoryIdToLong)
                        triviaPointsDao.createTriviaPoints(
                            TriviaPoints(
                                apiCategoryIdToLong,
                                apiCategory.name
                            )
                        )
                    }
                }
                mainActivity.loadFragment(AllCategories())
            }
        }
    }

    fun getCategoriesFromDb() = liveData {
        val categoriesFromDb = triviaPointsDao.findAll()
        emit(categoriesFromDb)
    }

}