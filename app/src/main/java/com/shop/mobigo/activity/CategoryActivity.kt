package com.shop.mobigo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.shop.mobigo.AppConstants
import com.shop.mobigo.AppSharedPreference
import com.shop.mobigo.R
import com.shop.mobigo.Utils
import com.shop.mobigo.adapter.CategoryAdapter
import com.shop.mobigo.models.JsonResponseModel
import com.shop.mobigo.models.ParentCategory
import com.shop.mobigo.viewModels.CategoryViewModel
import com.shop.mobigo.viewModels.JsonAPIViewModel
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : BaseActivity() {

    private val mTAG = CategoryActivity::class.java.name

    private val jsonAPIViewModel: JsonAPIViewModel by lazy {
        ViewModelProviders.of(this).get(JsonAPIViewModel::class.java)
    }

    private val categoryViewModel: CategoryViewModel by lazy {
        ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        initViews()
    }

    private fun initViews() {
        if (AppSharedPreference(this).getBooleanFromSharedPreference(AppConstants.IS_DATA_AVAILABLE)) {
            Log.i(mTAG, "data available , no need to call api")
            categoryViewModel.getCatList()
        } else {
            callAPI()
        }

        jsonAPIViewModel.jsonResponse.observe(this, Observer<JsonResponseModel> {
            AppSharedPreference(this).putBooleanToSharedPreference(AppConstants.IS_DATA_AVAILABLE, true)
            stopProgressBar()
        })

        jsonAPIViewModel.errorMessage.observe(this, Observer {
            stopProgressBar()
            Log.i(mTAG, it)
            showAlertDialog(getString(R.string.msg_api_error))

        })

        jsonAPIViewModel.catList.observe(this, Observer {
           setData(it)
        })
        categoryViewModel.catList.observe(this, Observer {
            setData(it)
        })
    }

    private fun callAPI() {
        if (Utils.isNetworkConnected(this)) {
            showProgressBar()
            jsonAPIViewModel.getData()
        } else {
            showToastMessage(getString(R.string.msg_network))
        }
    }

    private fun setData(catList: List<ParentCategory>?) {
        recyclerTypes.layoutManager = GridLayoutManager(this, 2)
        if (catList == null) {
            showToastMessage("Data not available , please try after some time.")
        } else {
            recyclerTypes.adapter = CategoryAdapter(catList)
        }
    }
}
