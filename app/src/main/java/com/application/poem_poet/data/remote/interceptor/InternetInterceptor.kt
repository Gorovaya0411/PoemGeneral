package com.application.poem_poet.data.remote.interceptor

import com.application.poem_poet.data.service.InternetConnectionService
import com.application.poem_poet.extension.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class InternetInterceptor @Inject constructor(private var connectionService: InternetConnectionService) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!connectionService.isConnected())
                throw NoInternetException(
                    RuntimeException()
                )
            return chain.proceed(chain.request())
        } catch (e: IOException) {
            if (!connectionService.isConnected())
                throw NoInternetException(e)
            throw e
        }
    }
}