package com.application.poem_poet.domain


import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface CommunityUseCase {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var saveNamePoetAddFragment: String?
    var saveIdPoetAddFragment: String?
    var saveLoginUser: String?
    var saveIdUser: String?
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

    override var saveNamePoetAddFragment: String?
        get() = sessionStoreService.saveNamePoetAddFragment
        set(value) {
            sessionStoreService.saveNamePoetAddFragment = value
        }

    override var saveIdPoetAddFragment: String?
        get() = sessionStoreService.saveIdPoetAddFragment
        set(value) {
            sessionStoreService.saveIdPoetAddFragment = value
        }

    override var saveLoginUser: String?
        get() = sessionStoreService.saveLoginUser
        set(value) {
            sessionStoreService.saveLoginUser = value
        }

    override var saveIdUser: String?
        get() = sessionStoreService.saveIdUser
        set(value) {
            sessionStoreService.saveIdUser = value
        }
}