package com.application.poem_poet.service

import com.application.poem_poet.db.SessionStore
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.model.UserGeneral
import com.application.poem_poet.model.UserGeneralSave
import javax.inject.Inject

interface SessionStoreService {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var saveUserGeneral: UserGeneralSave
    var savePoemAnswer: PoemAnswer
    var saveUser: User
}

class SessionStoreServiceImpl @Inject constructor(private val sessionStore: SessionStore) :
    SessionStoreService {

    override var checkDetailedFragment: String?
        get() = sessionStore.checkDetailedFragment
        set(value) {
            sessionStore.checkDetailedFragment = value
        }

    override var checkCropFragment: String?
        get() = sessionStore.checkCropFragment
        set(value) {
            sessionStore.checkCropFragment = value
        }

    override var savePoemAnswer: PoemAnswer
        get() = sessionStore.savePoemAnswer
        set(value) {
            sessionStore.savePoemAnswer = value
        }

    override var saveUserGeneral: UserGeneralSave
        get() = sessionStore.saveUserGeneral
        set(value) {
            sessionStore.saveUserGeneral = value
        }

    override var saveUser: User
        get() = sessionStore.saveUser
        set(value) {
            sessionStore.saveUser = value
        }
}