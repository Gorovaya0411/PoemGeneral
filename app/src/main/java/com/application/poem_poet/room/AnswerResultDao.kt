package com.application.poem_poet.room

import androidx.room.*
import com.application.poem_poet.model.PoemAnswer

@Dao
interface AnswerResultDao {
    @Query("SELECT * FROM PoemAnswer")
    fun getAll(): MutableList<PoemAnswer?>

    @Query("SELECT * FROM PoemAnswer WHERE id = :id")
    fun getById(id: Int): PoemAnswer

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(kist: PoemAnswer?)

    @Delete
    fun delete(employee: PoemAnswer?)

}