package com.smarteryo.poem_poet.data.service

import android.net.ConnectivityManager
import javax.inject.Inject

interface InternetConnectionService {
    fun isConnected(): Boolean
}

class InternetConnectionServiceImpl @Inject constructor(private val connectivityManager: ConnectivityManager) :
    InternetConnectionService {
    override fun isConnected(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}