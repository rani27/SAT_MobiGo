package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.repository.RankingRepository

class RankingViewModel(application: Application) : AndroidViewModel(application), OnDBListener {
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()
    private val repository: RankingRepository
    var rankingList: MutableLiveData<List<String>> = MutableLiveData()

    init {
        repository = RankingRepository(dataDAO)
    }

    override fun onRecordRetrieve(response: Any) {
        rankingList.value = response as List<String>
    }

    fun getRankingList() {
        repository.getRankingList(this)
    }
}