package com.shop.mobigo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.shop.mobigo.AppConstants
import com.shop.mobigo.Utils
import com.shop.mobigo.database.AppDatabase
import com.shop.mobigo.database.DataDao
import com.shop.mobigo.listeners.OnDBListener
import com.shop.mobigo.models.ProductValues
import com.shop.mobigo.models.Products
import com.shop.mobigo.repository.ProductRepository
import java.util.*

class ProductViewModel(application: Application) : AndroidViewModel(application), OnDBListener {
    private val dataDAO: DataDao = AppDatabase.getInstance(application)!!.dataDao()
    private val repository: ProductRepository
    private var mSelectedSort: Int = AppConstants.SORT_NEWEST_FIRST

    var productsList: MutableLiveData<List<ProductValues>> = MutableLiveData()

    init {
        repository = ProductRepository(dataDAO)
    }

    override fun onRecordRetrieve(response: Any) {
        productsList.value = response as List<ProductValues>
        sortList()
    }

    fun getProductsBySelectedValue(selectedValue: String) {
        repository.getProductsBySelectedCat(selectedValue, this)
    }

    fun getProductsByRanking(selectedCat: String, selectedRanking: String) {
        repository.getProductsByRanking(selectedCat, selectedRanking, this)
    }

    fun setCurrentSort(selectedSort: Int) {
        mSelectedSort=selectedSort
        sortList()
    }

    fun getCurrentSort():Int{
        return mSelectedSort
    }

    private fun sortList() {
        when (mSelectedSort) {
            AppConstants.SORT_NEWEST_FIRST -> {
              Collections.sort(productsList.value, kotlin.Comparator { o1, o2 ->
                    return@Comparator Utils.getDateFromString(o2.date_added).compareTo(Utils.getDateFromString(o1.date_added))
                })
            }
            AppConstants.SORT_PRICE_LTH -> {
                Collections.sort(productsList.value, kotlin.Comparator { o1, o2 ->
                    return@Comparator o1.product_price.compareTo(o2.product_price)
                })
            }
            AppConstants.SORT_PRICE_HTL -> {
                Collections.sort(productsList.value, kotlin.Comparator { o1, o2 ->
                    return@Comparator o2.product_price.compareTo(o1.product_price)
                })
            }
        }
        productsList.value=productsList.value
    }

}