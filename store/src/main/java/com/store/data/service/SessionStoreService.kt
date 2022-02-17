package com.store.data.service

import com.store.sessionstore.SessionStore
import javax.inject.Inject

interface SessionStoreService {

}

class SessionStoreServiceImpl @Inject constructor(
    val sessionStore: SessionStore
) : SessionStoreService {


}