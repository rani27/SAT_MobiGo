package com.shop.mobigo

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreference(context : Context)
{
    private val prefs: SharedPreferences = context.getSharedPreferences(AppConstants.PREFS_FILENAME, 0);

    fun getBooleanFromSharedPreference(key :String):Boolean
    {
        return prefs.getBoolean(key, false)
    }
    fun putBooleanToSharedPreference(key :String,value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
}