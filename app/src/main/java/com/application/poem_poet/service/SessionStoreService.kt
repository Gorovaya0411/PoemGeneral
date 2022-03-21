package com.application.poem_poet.service

import com.application.poem_poet.db.SessionStore
import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import javax.inject.Inject

interface SessionStoreService {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var saveUser: User
    var savePoemAnswer: PoemAnswer
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

    override var saveUser: User
        get() = sessionStore.saveUser
        set(value) {
            sessionStore.saveUser = value
        }

}