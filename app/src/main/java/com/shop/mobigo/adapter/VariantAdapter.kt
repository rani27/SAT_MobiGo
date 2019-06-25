package com.shop.mobigo.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shop.mobigo.R
import com.shop.mobigo.listeners.OnVariantChangeListener
import kotlinx.android.synthetic.main.variants_row_layout.view.*


class VariantAdapter(private val type: Int, private val variantList: List<String>, private val listener: OnVariantChangeListener) : RecyclerView.Adapter<VariantAdapter.ViewHolder>() {
    private lateinit var mContext: Context
    private var mSelectedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.variants_row_layout, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return variantList.size;
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (mSelectedPosition == position) {
            viewHolder.mTxtValue.isSelected = true
            viewHolder.mTxtValue.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
        } else {
            viewHolder.mTxtValue.isSelected = false
            viewHolder.mTxtValue.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        }
        viewHolder.mTxtValue.text = variantList[position]

        viewHolder.mParentLayout.setOnClickListener {
            mSelectedPosition = viewHolder.adapterPosition
            listener.onVariantChanged(type, variantList[viewHolder.adapterPosition])
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTxtValue: TextView = itemView.txtValue
        val mParentLayout: ConstraintLayout = itemView.parent_layout

    }
}