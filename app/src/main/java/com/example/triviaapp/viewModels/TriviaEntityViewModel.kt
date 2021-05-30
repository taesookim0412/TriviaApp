package com.example.triviaapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.triviaapp.api.TriviaResultsResponse
import com.example.triviaapp.schemas.Trivia.TriviaPoints
import com.example.triviaapp.schemas.Trivia.TriviaPointsRepository
import com.example.triviaapp.utils.DecodeHtml
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class TriviaEntityViewModel() : ViewModel() {
    private val triviaPointsRepository = TriviaPointsRepository()
    var triviaEntityResponse: TriviaResultsResponse = TriviaResultsResponse()
    var question = ""
    var answer1 = ""
    var answer2 = ""
    var answer3 = ""
    var answer4 = ""
    var category = ""
    var correct_answer = ""

    fun reset() {
        triviaEntityResponse = TriviaResultsResponse()
        question = triviaEntityResponse.question
        answer1 = triviaEntityResponse.correct_answer
        answer2 = triviaEntityResponse.incorrect_answers[0]
        answer3 = triviaEntityResponse.incorrect_answers[1]
        answer4 = triviaEntityResponse.incorrect_answers[2]
        category = triviaEntityResponse.category
        correct_answer = triviaEntityResponse.correct_answer
    }

    fun setTrivia(trivia: TriviaResultsResponse) {
        reset()
        triviaEntityResponse = trivia
        question = trivia.question
        category = trivia.category
        correct_answer = trivia.correct_answer;
        if (trivia.type == "boolean") {
            answer1 = "True";
            answer2 = "False";
            return
        }
        //add to list and shuffle then assign properties.
        val answers = ArrayList<String>()
        //add to list
        answers.add(trivia.correct_answer);
        for (incorrect_answer in trivia.incorrect_answers) {
            answers.add(incorrect_answer)
        }
        //shuffle
        answers.shuffle()

        //assign
        answer1 = answers[0];
        answer2 = answers[1];
        if (answers.size > 2) {
            answer3 = answers[2];
        }
        if (answers.size > 3) {
            answer4 = answers[3];
        }
    }

    fun onClickAnswer(selection: String): Boolean {
        val res = this.correct_answer == selection;
        CoroutineScope(Dispatchers.IO).launch {
            val categoryDbEntity =
                triviaPointsRepository.triviaPointsDao.findByCategory(this@TriviaEntityViewModel.category)
            val plusPoints = categoryDbEntity.points?.plus(1)
            val minusPoints = categoryDbEntity.points?.minus(1)
            if (res) {
                //Increment Category by name
                triviaPointsRepository.triviaPointsDao.updateTriviaPoints(
                    TriviaPoints(
                        categoryDbEntity.id,
                        categoryDbEntity.apiId,
                        categoryDbEntity.category,
                        plusPoints
                    )
                )
            } else {
                triviaPointsRepository.triviaPointsDao.updateTriviaPoints(
                    TriviaPoints(
                        categoryDbEntity.id,
                        categoryDbEntity.apiId,
                        categoryDbEntity.category,
                        minusPoints
                    )
                )
            }
        }
        return res
    }


}