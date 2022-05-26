package com.application.poem_poet.domain

import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface MainUseCase {
    var saveUidUser: String?
}

class MainUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService
) : MainUseCase {
    override var saveUidUser: String?
        get() = sessionStoreService.saveUidUser
        set(value) {
            sessionStoreService.saveUidUser = value
        }
}