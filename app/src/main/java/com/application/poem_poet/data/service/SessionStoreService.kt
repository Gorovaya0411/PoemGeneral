package com.application.poem_poet.data.service

import com.application.poem_poet.db.SessionStore
import javax.inject.Inject

interface SessionStoreService {
    var checkSound: Boolean
    var checkNumHive: Int
    var quantityOpenCard: Int
    var maxScore: Int
    var duration: Int
    var score: Int
}

class SessionStoreServiceImpl @Inject constructor(private val sessionStore: SessionStore) :
    SessionStoreService {

    override var checkSound: Boolean
        get() = sessionStore.checkSound
        set(value) {
            sessionStore.checkSound = value
        }

    override var checkNumHive: Int
        get() = sessionStore.checkNumHive
        set(value) {
            sessionStore.checkNumHive = value
        }

    override var quantityOpenCard: Int
        get() = sessionStore.quantityOpenCard
        set(value) {
            sessionStore.quantityOpenCard = value
        }

    override var score: Int
        get() = sessionStore.score
        set(value) {
            sessionStore.score = value
        }

    override var maxScore: Int
        get() = sessionStore.maxScore
        set(value) {
            sessionStore.maxScore = value
        }

    override var duration: Int
        get() = sessionStore.duration
        set(value) {
            sessionStore.duration = value
        }
}