package com.shop.mobigo.repository

import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnApiResponseListener
import com.shop.mobigo.models.*
import com.shop.mobigo.network.JsonAPIHelper

class JsonAPIRepository() {
    fun getJsonData(listener: OnApiResponseListener) {
        JsonAPIHelper().getData(listener)
    }
}