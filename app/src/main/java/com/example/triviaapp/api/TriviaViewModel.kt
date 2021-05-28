package com.example.triviaapp.api


import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.*
/*import com.allydev.ally.schemas.trivia.categories.TriviaCategoriesRepository*/
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TriviaViewModel(application: Application): AndroidViewModel(application) {
    val sharedPref =
        getApplication<Application>().getSharedPreferences("triviaSettings", MODE_PRIVATE)
/*    var categorySetting: MutableLiveData<String?> = MutableLiveData<String?>().apply {
        value = sharedPref.getString("category", "")
    }
    var difficultySetting: MutableLiveData<String?> = MutableLiveData<String?>().apply {
        value = sharedPref.getString("difficulty", "")
    }
    var typeSetting: MutableLiveData<String?> = MutableLiveData<String?>().apply {
        value = sharedPref.getString("typeSetting", "")
    }*/

    // API
    val apiRepository: TriviaAPIRepository = TriviaAPIRepository()

    //Handle errors in the function?
    val oneQuestion: LiveData<TriviaDataResponse?> = liveData {
        emit(apiRepository.getQuestion())
    }
    val getCategoriesFromAPI = liveData {
        val data = apiRepository.getCategories()
        val categoriesFromApi: Array<TriviaCategoryResultsResponse>? = data?.trivia_categories
        //TODO store into room (fin?)

        emit(categoriesFromApi);
    }
}
//    val getCategoriesFromAPI = liveData {
//        val data = apiRepository.getCategories()
//        val categoriesFromApi: Array<TriviaCategoryResultsResponse>? = data?.trivia_categories
//        //TODO store into room (fin?)
//
//        val categoryEntities = triviaCategoryRepository.findAll()
//        Log.d("Category Entities: ", categoryEntities?.size.toString())
//
//        //true = Don't delete
//        //false = Delete all
//        //if size = 0 Don't delete
//        val isEmptyDB: Boolean = (categoriesFromApi?.size == 0)
//        var isEqual = false
//
//        Log.d(categoriesFromApi?.size.toString(), categoriesFromApi?.size.toString())
//        Log.d(isEmptyDB.toString(), isEmptyDB.toString())
//
//        // If we have entities then scan each entity is the same as the api and if both indexes are equal then pass true
//        if (categoryEntities?.isNotEmpty() ?: false) {
//            Log.d("", "2")
//            var passedIndex = 0
//            categoryEntities?.forEachIndexed { index, roomEnt ->
//                if (roomEnt.id != categoriesFromApi?.get(index)?.id || roomEnt.name != categoriesFromApi?.get(
//                        index
//                    )?.name
//                )
//                    return@forEachIndexed
//                passedIndex++
//            }
//            if (passedIndex == categoryEntities?.size) isEqual = true
//        }
//
//        //If DB is NOT empty then do the following
//        if (isEmptyDB == false) {
//            if (isEqual == true) {
//                Log.d("Result", "True they are equal")
//                emit(categoryEntities)
//            } else if (isEqual == false) {
//                categoriesEnt_WipeAndInsert(categoriesFromApi)
//            }
//        } else if (isEmptyDB == true) {
//            categoriesEnt_WipeAndInsert(categoriesFromApi)
//        }
//
//    }
//
//    private suspend fun categoriesEnt_WipeAndInsert(categoriesFromApi: Array<TriviaCategoryResultsResponse>?) {
//        Log.d("", "Wipe the database, add each response")
//        triviaCategoryRepository.deleteAllCategories()
//        val newCategoryEntities = arrayOfNulls<TriviaCategoriesEntity>(categoriesFromApi?.size ?: 0)
//        categoriesFromApi?.forEachIndexed { idx, it ->
//            val entity = TriviaCategoriesEntity(it.id, it.name)
//            newCategoryEntities[idx] = entity
//        }
//        triviaCategoryRepository.insertCategories(*newCategoryEntities)
//    }
//
//    fun putCustom50Questions(category: String = "", difficulty: String = "", type: String = "") =
//        viewModelScope.launch(Dispatchers.IO) {
//            val data: Array<TriviaResultsResponse>? =
//                apiRepository.get50CustomQuestions(category, difficulty, type)
//            if (data != null) {
//                createTrivia(*data)
//            }
//        }
//
//
//    // Database
//    val dataRepository: TriviaDataRepository = TriviaDataRepository(application)
//    val triviaCategoryRepository: TriviaCategoriesRepository =
//        TriviaCategoriesRepository(application)
//    /*val categoryRepository: TriviaCategoriesRepository = TriviaCategoriesRepository(application)*/
//
//    val allTrivia: LiveData<List<TriviaDataEntity>>? = dataRepository.findAll()
//    fun createTrivia(vararg responses: TriviaResultsResponse) =
//        viewModelScope.launch(Dispatchers.IO) {
//            dataRepository.insertTrivia(*responses)
//        }
//
//    fun deleteTrivia(vararg trivia: TriviaDataEntity) = viewModelScope.launch(Dispatchers.IO) {
//        dataRepository.deleteTrivia(*trivia)
//    }
//
//
///*    fun setTriviaSettings(category: String = "", difficulty: String = "", type: String = "") {
//        val edit: SharedPreferences.Editor = sharedPref.edit()
//        edit.putString("category", category)
//        edit.putString("difficulty", difficulty)
//        edit.putString("type", type)
//        edit.apply()
//        categorySetting.value = sharedPref.getString("category", "")
//        difficultySetting.value = sharedPref.getString("difficulty", "")
//        typeSetting.value = sharedPref.getString("type", "")
//    }*/
//}