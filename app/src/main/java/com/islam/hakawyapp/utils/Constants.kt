package com.islam.hakawyapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

const val SH_PREFS_NAME = "HakawyApp"
const val SH_PREFS_USER = "User"
const val SH_PREFS_SIGN_IN_STATUS = "SignIn"

const val NONE = "None"


const val COLLECTION_USERS = "Users"


const val ERROR_CODE_PHONE_NUMBER = 0
const val ERROR_CODE_SERVER = 1
const val ERROR_CODE_NETWORK_CONNECTION = 2
const val ERROR_CODE_UNKNOWN = 3
const val ERROR_CODE_NO_INTERNET = 4

const val ERROR_CODE_VERIFICATION_CODE = 5
const val ERROR_CODE_USER_NETWORK_CONNECTION = 6
const val ERROR_CODE_USER_UNKNOWN =7
const val ERROR_CODE_USER_NO_INTERNET = 8


fun isInternetConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        if (network != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo
        nwInfo != null && nwInfo.isConnected
    }
}