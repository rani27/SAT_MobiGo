package com.shop.mobigo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParentCategory(var cat_name: String,
                          var sub_categories: String = "") : Parcelable {
    constructor() : this("", "")
}