package com.shop.mobigo.network

import com.shop.mobigo.models.JsonResponseModel
import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("json")
    fun getData() : Call<JsonResponseModel>
}