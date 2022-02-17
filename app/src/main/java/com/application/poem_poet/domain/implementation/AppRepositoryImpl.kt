package com.application.poem_poet.domain.implementation

import com.application.poem_poet.domain.repository.AppRepository
import com.store.data.service.SessionStoreService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val sessionStoreService: SessionStoreService
) : AppRepository {

}