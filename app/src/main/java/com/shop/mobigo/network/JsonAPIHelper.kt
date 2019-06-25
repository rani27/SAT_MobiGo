package com.shop.mobigo.network

import android.util.Log
import com.shop.mobigo.listeners.OnApiResponseListener
import com.shop.mobigo.models.JsonResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JsonAPIHelper {
    private val mTAG = JsonAPIHelper::class.java.name
    fun getData(listener: OnApiResponseListener) {
        Log.i(mTAG, "making getData API call")
        val call = RetrofitService.create().getData()
        call.enqueue(object : Callback<JsonResponseModel> {
            override fun onFailure(call: Call<JsonResponseModel>?, t: Throwable?) {
                Log.i(mTAG, "Api error, ${t?.message}")
                t?.message?.let { listener.onError(it) }
            }
            override fun onResponse(call: Call<JsonResponseModel>?, response: Response<JsonResponseModel>) {
                Log.i(mTAG, "api success")
                response.body()?.let { listener.onCompleted(it) }
            }
        })
    }

}