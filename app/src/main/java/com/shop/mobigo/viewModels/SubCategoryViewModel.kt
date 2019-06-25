package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.ParentCategory
import com.shop.mobigo.repository.SubCategoryRepository

class SubCategoryViewModel(application: Application) : AndroidViewModel(application), OnDBListener {
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()
    private val repository: SubCategoryRepository
    var subCatList: MutableLiveData<List<ParentCategory>> = MutableLiveData()

    init {
        repository = SubCategoryRepository(dataDAO)
    }

    override fun onRecordRetrieve(response: Any) {
        subCatList.value = response as List<ParentCategory>
    }

    private fun getSubCatId(subCat: String): List<Int> {
        val subCatList: ArrayList<Int> = mutableListOf<Int>() as ArrayList<Int>
        val subCatStr = subCat.replace("\\s".toRegex(), "")
        val subCatIds = subCatStr.split("[", ",", "]")
        for (id in subCatIds) {
            if (!id.isBlank()) {
                subCatList.add(id.toInt())
            }
        }
        return subCatList

    }

    fun getSubCatList(subCatIds: String) {
        repository.getSubCatList(getSubCatId(subCatIds), this)
    }

}