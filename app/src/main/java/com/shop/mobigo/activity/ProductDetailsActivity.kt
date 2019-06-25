package com.shop.mobigo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.shop.mobigo.AppConstants
import com.shop.mobigo.R
import com.shop.mobigo.adapter.VariantAdapter
import com.shop.mobigo.listeners.OnVariantChangeListener
import com.shop.mobigo.viewModels.VariantsViewModel
import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailsActivity : BaseActivity(), OnVariantChangeListener {
    private val variantsViewModel: VariantsViewModel by lazy {
        ViewModelProviders.of(this).get(VariantsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        initViews()
    }

    private fun initViews() {
        btnAddToCart.setOnClickListener {
            showToastMessage(getString(R.string.msg_added_to_cart))
        }
        btnBuy.setOnClickListener {
            showToastMessage(getString(R.string.msg_order_placed))
        }

        recyclerViewProductSize.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerViewProductColors.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        showProgressBar()
        variantsViewModel.getVariants(intent.getStringExtra(AppConstants.SELECTED_TYPE))

        variantsViewModel.mSizeList.observe(this, Observer {
            stopProgressBar()
            txtProductName.text = intent.getStringExtra(AppConstants.SELECTED_TYPE)
            recyclerViewProductSize.adapter = it?.let { it1 -> VariantAdapter(AppConstants.SIZE, it1, this) }
        })

        variantsViewModel.mColorList.observe(this, Observer {
            recyclerViewProductColors.adapter = it?.let { it1 -> VariantAdapter(AppConstants.COLOR, it1, this) }
        })

        variantsViewModel.productPrice.observe(this, Observer {
            txtProductPrice.text = getString(R.string.lbl_price, it)
        })
    }

    override fun onVariantChanged(type: Int, value: String) {
        if (type == AppConstants.SIZE) {
            variantsViewModel.setSize(value.toInt())
        } else if (type == AppConstants.COLOR) {
            variantsViewModel.setColor(value)
        }
    }

}
