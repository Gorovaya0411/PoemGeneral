package com.application.poem_poet.domain

import com.application.poem_poet.model.User
import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface MainUseCase {
    var saveUser: User
}

class MainUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService
) : MainUseCase {
    override var saveUser: User
        get() = sessionStoreService.saveUser
        set(value) {
            sessionStoreService.saveUser = value
        }
}