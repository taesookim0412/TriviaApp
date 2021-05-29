package com.example.triviaapp.schemas.Trivia

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "triviapoints")
data class TriviaPoints (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "points") val points: Long?
){
    constructor(category: String?):
            this(null, category, 0)
}