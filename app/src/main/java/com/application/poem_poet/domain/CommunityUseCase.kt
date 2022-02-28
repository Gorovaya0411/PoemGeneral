package com.application.poem_poet.domain


import com.application.poem_poet.repository.CommunityRepository
import javax.inject.Inject

class CommunityUseCase @Inject constructor(private val charactersDetailedRepository: CommunityRepository)