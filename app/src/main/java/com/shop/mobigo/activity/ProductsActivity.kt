package com.shop.mobigo.activity

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.shop.mobigo.AppConstants
import com.shop.mobigo.R
import com.shop.mobigo.adapter.ProductsAdapter
import com.shop.mobigo.models.ProductValues
import com.shop.mobigo.viewModels.ProductViewModel
import com.shop.mobigo.viewModels.RankingViewModel
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.filter_dialog.*
import kotlinx.android.synthetic.main.sort_layout.*
import java.util.*


class ProductsActivity : BaseActivity() {

    private val productViewModel: ProductViewModel by lazy {
        ViewModelProviders.of(this).get(ProductViewModel::class.java)
    }
    private val rankingViewModel: RankingViewModel by lazy {
        ViewModelProviders.of(this).get(RankingViewModel::class.java)
    }

    private var rankingList: ArrayList<String>? = null
    private var mAdapter: ProductsAdapter? = null
    private lateinit var mProductList: ArrayList<ProductValues>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        initViews()
    }

    private fun initViews() {
        setUpToolBar(toolbar, intent.getStringExtra(AppConstants.SELECTED_TYPE))
        showProgressBar()

        productViewModel.getProductsBySelectedValue(intent.getStringExtra(AppConstants.SELECTED_TYPE))
        rankingViewModel.getRankingList()

        recyclerViewProducts.layoutManager = GridLayoutManager(this, 2)
        txtFilter.setOnClickListener {
            showFilterDialog()
        }

        rankingViewModel.rankingList.observe(this, Observer {
            rankingList = it as ArrayList<String>?
            rankingList?.add(getString(R.string.lbl_remove))

        })
        productViewModel.productsList.observe(this, Observer {
            stopProgressBar()
            mProductList = it as ArrayList<ProductValues>
            setData()
        })

        txtSort.setOnClickListener {
            showSortingAlert()
        }
    }

    private fun setData() {
        if (mAdapter == null) {
            mAdapter = ProductsAdapter(mProductList)
            recyclerViewProducts.adapter = mAdapter
        } else {
            mProductList.let { mAdapter!!.setUpdatedData(it) }
        }
    }

    private fun showFilterDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.filter_dialog)
        for (rank in this.rankingList!!) {
            val txtView = TextView(this)
            txtView.setTextColor(ContextCompat.getColor(this, R.color.black))
            txtView.text = rank
            txtView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            txtView.setPadding(0, 20, 0, 20)
            txtView.gravity = Gravity.START
            dialog.layoutRanking.addView(txtView)

            val divider = TextView(this)
            divider.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
            divider.setPadding(0, 10, 0, 10)
            divider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            dialog.layoutRanking.addView(divider)

            txtView.setOnClickListener {
                dialog.dismiss()
                sortProductsByRank(txtView.text.toString())
            }
        }
        dialog.show()
    }

    private fun sortProductsByRank(selectedRanking: String) {
        showProgressBar()
        if (selectedRanking == getString(R.string.lbl_remove)) {
            productViewModel.getProductsBySelectedValue(intent.getStringExtra(AppConstants.SELECTED_TYPE))
            toolbar.title = intent.getStringExtra(AppConstants.SELECTED_TYPE)

        } else {
            productViewModel.getProductsByRanking(intent.getStringExtra(AppConstants.SELECTED_TYPE), selectedRanking)
            toolbar.title = "${intent.getStringExtra(AppConstants.SELECTED_TYPE)}-> $selectedRanking"
        }
    }

    private fun showSortingAlert() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.sort_layout)
        if (productViewModel.getCurrentSort() == AppConstants.SORT_NEWEST_FIRST) {
            dialog.btnNewestFirst.isChecked = true
        } else if (productViewModel.getCurrentSort() == AppConstants.SORT_PRICE_LTH) {
            dialog.btnPriceLTH.isChecked = true

        } else if (productViewModel.getCurrentSort() == AppConstants.SORT_PRICE_HTL) {
            dialog.btnPriceHTL.isChecked = true
        }
        dialog.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            dialog.dismiss()
            when (checkedId) {
                R.id.btnNewestFirst -> {
                    productViewModel.setCurrentSort(AppConstants.SORT_NEWEST_FIRST)
                }
                R.id.btnPriceLTH -> {
                    productViewModel.setCurrentSort(AppConstants.SORT_PRICE_LTH)
                }
                R.id.btnPriceHTL -> {
                    productViewModel.setCurrentSort(AppConstants.SORT_PRICE_HTL)
                }
            }
        }
        dialog.show()
    }
}
