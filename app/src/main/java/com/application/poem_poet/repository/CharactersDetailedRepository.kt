package com.application.poem_poet.repository

import com.application.poem_poet.App
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.room.AnswerResultDao
import com.application.poem_poet.room.AppDatabase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharactersDetailedRepository @Inject constructor() {
    private val db: AppDatabase = App.getDatabase()
    var employeeDao: AnswerResultDao = db.answerResultDao()

    fun getCharactersByID(id: PoemAnswer?): Single<Unit> {
        return Single.fromCallable {
            return@fromCallable employeeDao.insertAll(id)
        }
    }

    fun getAllCharacters(): Single<MutableList<PoemAnswer?>> {
        return Single.fromCallable {
            return@fromCallable employeeDao.getAll()
        }
    }
}