package com.application.poem_poet.service

import com.application.poem_poet.db.SessionStore
import javax.inject.Inject

interface SessionStoreService {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var saveNamePoetAddFragment: String?
    var saveIdPoetAddFragment: String?
    var saveLoginUser: String?
    var saveIdUser: String?
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

    override var saveNamePoetAddFragment: String?
        get() = sessionStore.saveNamePoetAddFragment
        set(value) {
            sessionStore.saveNamePoetAddFragment = value
        }

    override var saveIdPoetAddFragment: String?
        get() = sessionStore.saveIdPoetAddFragment
        set(value) {
            sessionStore.saveIdPoetAddFragment = value
        }

    override var saveLoginUser: String?
        get() = sessionStore.saveLoginUser
        set(value) {
            sessionStore.saveLoginUser = value
        }

    override var saveIdUser: String?
        get() = sessionStore.saveIdUser
        set(value) {
            sessionStore.saveIdUser = value
        }
}