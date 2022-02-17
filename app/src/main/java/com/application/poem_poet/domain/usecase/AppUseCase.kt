package com.application.poem_poet.domain.usecase

import com.application.poem_poet.domain.repository.AppRepository
import com.store.data.service.SessionStoreService
import javax.inject.Inject

interface AppUseCase {

}

class AppUseCaseImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService,
    private val appRepository: AppRepository
) : AppUseCase {


}