package com.application.poem_poet.domain

import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface CommunityUseCase {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var savePoemHelp: PoemHelp
    var saveUserGeneral: UserGeneralSave
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

    override var savePoemHelp: PoemHelp
        get() = sessionStoreService.savePoemHelp
        set(value) {
            sessionStoreService.savePoemHelp = value
        }

    override var saveUserGeneral: UserGeneralSave
        get() = sessionStoreService.saveUserGeneral
        set(value) {
            sessionStoreService.saveUserGeneral = value
        }
}