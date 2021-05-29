package com.example.triviaapp.data

data class Trivia(val category:String,
                  val type: String,
                  val difficulty: String,
                  val question: String,
                  val correct_answer: String,
                  val incorrect_answers: Array<String>
)