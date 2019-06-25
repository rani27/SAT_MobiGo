package com.shop.mobigo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Categories(@PrimaryKey @ColumnInfo(name = "cat_name") var name: String,
                      var id: Int,
                      var isParent: Boolean,
                      var sub_categories: String,
                      @Ignore var products: List<Products>,
                      @Ignore var child_categories: Array<Int>) {
    constructor() : this("", 0, false, "", emptyList(), emptyArray())
}

