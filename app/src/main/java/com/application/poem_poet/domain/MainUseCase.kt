package com.application.poem_poet.domain

import com.application.poem_poet.repository.MainRepository
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository)