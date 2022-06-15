package com.application.poem_poet.domain

import com.application.poem_poet.model.PoemAnswer
import com.application.poem_poet.model.PoemHelp
import com.application.poem_poet.model.UserGeneralSave
import com.application.poem_poet.repository.CharactersDetailedRepository
import com.application.poem_poet.service.SessionStoreService
import io.reactivex.Single
import javax.inject.Inject

interface CommunityUseCase {
    var checkDetailedFragment: String?
    var checkCropFragment: String?
    var savePoemHelp: PoemHelp
    var saveUserGeneral: UserGeneralSave
    var saveUidUser: String?
}

class CommunityUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService,
    private val charactersDetailedRepository: CharactersDetailedRepository
) : CommunityUseCase {

    override var checkDetailedFragment: String?
        get() = sessionStoreService.checkDetailedFragment
        set(value) {
            sessionStoreService.checkDetailedFragment = value
        }

    fun getCharactersByID(id: PoemAnswer?): Single<Unit> =
        charactersDetailedRepository.getCharactersByID(id)

    fun getAllCharacters(): Single<MutableList<PoemAnswer?>> =
        charactersDetailedRepository.getAllCharacters()


    override var checkCropFragment: String?
        get() = sessionStoreService.checkCropFragment
        set(value) {
            sessionStoreService.checkCropFragment = value
        }

    override var saveUidUser: String?
        get() = sessionStoreService.saveUidUser
        set(value) {
            sessionStoreService.saveUidUser = value
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