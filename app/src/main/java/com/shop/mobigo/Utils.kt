package com.shop.mobigo

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            var connected = false
            val mgr = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = mgr.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected) {
                connected = true
            }
            return connected
        }

        fun getDateFromString(value: String): Date {
            val format = SimpleDateFormat(AppConstants.ADDED_DATE_FORMAT, Locale.ENGLISH)
            return format.parse(value)
        }
    }
}