package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.Variants
import com.shop.mobigo.repository.VariantsRepository
import java.util.*

class VariantsViewModel(application: Application) : AndroidViewModel(application), OnDBListener {
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()
    private val repository: VariantsRepository
    private var mSelectedSize: Int = 0
    private var mSelectedColor: String = ""
    private var variantsList: List<Variants> = mutableListOf()

    val mColorList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var productPrice: MutableLiveData<Int> = MutableLiveData()
    var mSizeList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        repository = VariantsRepository(dataDAO)
    }

    override fun onRecordRetrieve(response: Any) {
        variantsList = response as List<Variants>
        setSizeList()
    }

    fun getVariants(prod_name: String) {
        repository.getVariants(prod_name, this)
    }

    fun setColor(color: String) {
        mSelectedColor = color
        setPriceForSelectedValues()
    }

    fun setSize(size: Int) {
        mSelectedSize = size
        setColorValues()
        setPriceForSelectedValues()
    }

    private fun setColorValues() {
        val colorList: ArrayList<String> = ArrayList<String>()
        for (variant in variantsList) {
            if (variant.size == mSelectedSize) {
                colorList.add(variant.color)
            }
        }
        setColor(colorList[0])
        mColorList.value = colorList
    }

    private fun setPriceForSelectedValues() {
        for (variant in variantsList) {
            if (variant.size == mSelectedSize && variant.color.equals(mSelectedColor)) {
                productPrice.value = variant.price
            }
        }
    }

    private fun setSizeList() {
        val sizeList: ArrayList<String> = ArrayList<String>()
        setSize(variantsList.get(0).size)
        for (variant in variantsList) {
            if (variant.size != 0) {
                sizeList.add(variant.size.toString())
            }
        }
        val uniqueValue: HashSet<String> = HashSet()
        uniqueValue.addAll(sizeList)
        sizeList.clear()
        sizeList.addAll(uniqueValue)
        sizeList.sort()
        mSizeList.value = sizeList
    }
}