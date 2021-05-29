package com.example.triviaapp.viewModels

import androidx.lifecycle.ViewModel
import com.example.triviaapp.data.Trivia

class TriviaEntityViewModel(trivia: Trivia): ViewModel() {
    var question = ""
    var answer1 = ""
    var answer2 = ""
    var answer3 = ""
    var answer4 = ""
    var category = ""
    var correct_answer = ""

    init{
        question = trivia.question
        val answers = ArrayList<String>()
        correct_answer = trivia.correct_answer;
        answers.add(trivia.correct_answer);
        for (incorrect_answer in trivia.incorrect_answers){
            answers.add(incorrect_answer)
        }
        answers.shuffle()
        answer1 = answers[0];
        answer2 = answers[1];
        if (answers.size > 2){
            answer3 = answers[2];
        }
        if (answers.size > 3){
            answer4 = answers[3];
        }
    }


}