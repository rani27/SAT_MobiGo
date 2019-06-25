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
import com.shop.mobigo.activity.ProductsActivity
import com.shop.mobigo.activity.SubCategoryActivity
import com.shop.mobigo.listeners.OnSubCategoryChangeListener
import com.shop.mobigo.models.ParentCategory
import kotlinx.android.synthetic.main.category_row_layout.view.*


class SubCategoryAdapter(private val subCatList: List<ParentCategory>, private val listener: OnSubCategoryChangeListener) : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_row_layout, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subCatList.size;
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.mTxtTitle.text = subCatList.get(position).cat_name
        viewHolder.mParentLayout.setOnClickListener {
            if (subCatList.get(position).sub_categories.isBlank()) {
                val intent = Intent(mContext, ProductsActivity::class.java)
                intent.putExtra(AppConstants.SELECTED_TYPE, subCatList.get(position).cat_name)
                mContext.startActivity(intent)
            } else {
                val intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra(AppConstants.SELECTED_TYPE, subCatList.get(position))
                mContext.startActivity(intent)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTxtTitle: TextView = itemView.txt_title
        val mParentLayout: ConstraintLayout = itemView.parent_layout

    }
}