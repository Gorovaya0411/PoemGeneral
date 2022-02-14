package com.smarteryo.poem_poet.domain

import com.smarteryo.poem_poet.data.service.SessionStoreService
import javax.inject.Inject

interface MainUseCase {
    var checkSound: Boolean
    var checkNumHive: Int
    var quantityOpenCard: Int
    var score: Int
    var duration: Int
    var maxScore: Int
}

class MainUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService,
) : MainUseCase {

    override var checkSound: Boolean
        get() = sessionStoreService.checkSound
        set(value) {
            sessionStoreService.checkSound = value
        }

    override var checkNumHive: Int
        get() = sessionStoreService.checkNumHive
        set(value) {
            sessionStoreService.checkNumHive = value
        }

    override var quantityOpenCard: Int
        get() = sessionStoreService.quantityOpenCard
        set(value) {
            sessionStoreService.quantityOpenCard = value
        }


    override var score: Int
        get() = sessionStoreService.score
        set(value) {
            sessionStoreService.score = value
        }

    override var maxScore: Int
        get() = sessionStoreService.maxScore
        set(value) {
            sessionStoreService.maxScore = value
        }

    override var duration: Int
        get() = sessionStoreService.duration
        set(value) {
            sessionStoreService.duration = value
        }

}