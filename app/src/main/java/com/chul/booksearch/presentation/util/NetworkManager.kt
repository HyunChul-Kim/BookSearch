package com.chul.booksearch.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkManager constructor(private val context: Context){

    fun isNetworkConnected(): Boolean {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isNetworkConnectedApi23()
        } else {
            isNetworkConnectedUnderApi22()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnectedApi23(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    @Suppress("Deprecation")
    private fun isNetworkConnectedUnderApi22(): Boolean {
        val networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}