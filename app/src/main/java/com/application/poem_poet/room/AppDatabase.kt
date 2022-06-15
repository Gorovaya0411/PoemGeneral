package com.application.poem_poet.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.poem_poet.model.PoemAnswer

@Database(entities = [PoemAnswer::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerResultDao(): AnswerResultDao
}