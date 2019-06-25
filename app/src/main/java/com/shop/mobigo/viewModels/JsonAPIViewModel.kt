package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnApiResponseListener
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.JsonResponseModel
import com.shop.mobigo.models.ParentCategory
import com.shop.mobigo.repository.InsertDataRepository
import com.shop.mobigo.repository.JsonAPIRepository


class JsonAPIViewModel(application: Application) : AndroidViewModel(application), OnApiResponseListener, OnDBListener {
    var jsonResponse: MutableLiveData<JsonResponseModel> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var catList: MutableLiveData<List<ParentCategory>> = MutableLiveData()
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()

    private val repository = JsonAPIRepository()
    private val insertRepository = InsertDataRepository(dataDAO)

    fun getData() {
        repository.getJsonData(this)
    }

    private fun setResponse(response: JsonResponseModel) {

        jsonResponse.value = response
        if (jsonResponse.value != null) {
            insertRepository.insertDataToDB(jsonResponse.value!!, this)
        }
    }

    override fun onCompleted(response: Any) {
        setResponse(response as JsonResponseModel)
    }

    override fun onError(errorMessage: String) {
        this.errorMessage.value = errorMessage
    }

    override fun onRecordRetrieve(response: Any) {
        Log.i("Rani","inside jsonAPimodel")
        catList.value = response as List<ParentCategory>

    }
}