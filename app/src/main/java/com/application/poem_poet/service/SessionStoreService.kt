package com.application.poem_poet.service

import com.application.poem_poet.db.SessionStore
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import javax.inject.Inject

interface SessionStoreService {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var saveUserGeneral: UserGeneralSave
    var savePoemHelp: PoemHelp
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

    override var savePoemHelp: PoemHelp
        get() = sessionStore.savePoemHelp
        set(value) {
            sessionStore.savePoemHelp = value
        }

    override var saveUserGeneral: UserGeneralSave
        get() = sessionStore.saveUserGeneral
        set(value) {
            sessionStore.saveUserGeneral = value
        }
}