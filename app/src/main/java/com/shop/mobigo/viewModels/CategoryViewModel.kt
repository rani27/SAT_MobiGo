package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.*
import com.shop.mobigo.repository.CategoryRepository
import com.shop.mobigo.models.ParentCategory

/**
 * Created by Rani Ugale on 6/20/2019.
 */
class CategoryViewModel(application: Application): AndroidViewModel(application),OnDBListener
{
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()
    private val repository: CategoryRepository
    var catList: MutableLiveData<List<ParentCategory>> = MutableLiveData()

    init {
        repository= CategoryRepository(dataDAO)
    }

    override fun onRecordRetrieve(response: Any) {
        catList.value= response as List<ParentCategory>
    }

    fun getCatList() {
        repository.getCatList(this)
    }
}