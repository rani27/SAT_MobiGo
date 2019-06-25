package com.shop.mobigo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.shop.mobigo.AppConstants
import com.shop.mobigo.R
import com.shop.mobigo.adapter.SubCategoryAdapter
import com.shop.mobigo.listeners.OnSubCategoryChangeListener
import com.shop.mobigo.models.ParentCategory
import com.shop.mobigo.viewModels.SubCategoryViewModel
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : BaseActivity(), OnSubCategoryChangeListener {
    private val subCategoryViewModel: SubCategoryViewModel by lazy {
        ViewModelProviders.of(this).get(SubCategoryViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        initViews()
    }

    private fun initViews() {
        val selectedCat = intent.getParcelableExtra<ParentCategory>(AppConstants.SELECTED_TYPE)
        setUpToolBar(toolbar, selectedCat.cat_name)
        showProgressBar()
        subCategoryViewModel.getSubCatList(selectedCat.sub_categories)

        subCategoryViewModel.subCatList.observe(this, Observer {
            stopProgressBar()
            if (it != null) {
                setData(it)
            }
        })
    }

    private fun setData(valueList: List<ParentCategory>) {
        recyclerViewItemType.layoutManager = GridLayoutManager(this, 2)
        recyclerViewItemType.adapter = SubCategoryAdapter(valueList, this);
    }

    override fun onSubCategorySelected(subCatIds: String) {
        subCategoryViewModel.getSubCatList(subCatIds)
    }
}
