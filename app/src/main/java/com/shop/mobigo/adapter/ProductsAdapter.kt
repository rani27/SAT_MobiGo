package com.shop.mobigo.adapter

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shop.mobigo.AppConstants
import com.shop.mobigo.R
import com.shop.mobigo.activity.ProductDetailsActivity
import com.shop.mobigo.models.ProductValues
import kotlinx.android.synthetic.main.product_row_layout.view.*


class ProductsAdapter(private var productList: List<ProductValues>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.product_row_layout, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size;
    }

    fun setUpdatedData(updateList: List<ProductValues>) {
        productList=updateList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.mTxtTitle.text = productList[position].prod_name
        viewHolder.mTxtPrice.text=mContext.getString(R.string.lbl_price, productList[position].product_price)
        viewHolder.mParentLayout.setOnClickListener {
            val intent = Intent(mContext, ProductDetailsActivity::class.java)
            intent.putExtra(AppConstants.SELECTED_TYPE, productList[position].prod_name)
            mContext.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTxtTitle: TextView = itemView.txt_title
        val mParentLayout: ConstraintLayout = itemView.parent_layout
        val mTxtPrice: TextView = itemView.txtPrice

    }
}