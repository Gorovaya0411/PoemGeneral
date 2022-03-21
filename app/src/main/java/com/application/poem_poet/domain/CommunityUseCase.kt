package com.application.poem_poet.domain


import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.User
import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface CommunityUseCase {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var savePoemAnswer: PoemAnswer
    var saveUser: User
}

class CommunityUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService
) : CommunityUseCase {

    override var checkDetailedFragment: String?
        get() = sessionStoreService.checkDetailedFragment
        set(value) {
            sessionStoreService.checkDetailedFragment = value
        }

    override var checkCropFragment: String?
        get() = sessionStoreService.checkCropFragment
        set(value) {
            sessionStoreService.checkCropFragment = value
        }

    override var savePoemAnswer: PoemAnswer
        get() = sessionStoreService.savePoemAnswer
        set(value) {
            sessionStoreService.savePoemAnswer = value
        }

    override var saveUser: User
        get() = sessionStoreService.saveUser
        set(value) {
            sessionStoreService.saveUser = value
        }
}