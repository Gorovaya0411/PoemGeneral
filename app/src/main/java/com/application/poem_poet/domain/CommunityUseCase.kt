package com.application.poem_poet.domain


import com.application.poem_poet.service.SessionStoreService
import javax.inject.Inject

interface CommunityUseCase {
    var checkDetailedFragment: String?

}

class CommunityUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService
) : CommunityUseCase {
    override var checkDetailedFragment: String?
        get() = sessionStoreService.checkDetailedFragment
        set(value) {
            sessionStoreService.checkDetailedFragment = value
        }
}