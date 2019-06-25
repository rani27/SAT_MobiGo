package com.shop.mobigo.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.shop.mobigo.AppConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {
    companion object {
        private val TAG = RetrofitService::class.java.name
        var gson = GsonBuilder().setLenient().create()
        fun create(): APIService {
            Log.i(TAG, "inside RetrofitService")
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(AppConstants.BASE_URL)
                    .build()
            return retrofit.create(APIService::class.java)
        }
    }
}