package com.spectrum.task.helper

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

object TaskHelper {
    /**
     * Check Internet connection if not shows toast message
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = !(activeNetworkInfo == null || !activeNetworkInfo.isConnected)
        if (!isConnected) {
            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT)
                .show()
        }
        return isConnected
    }

}