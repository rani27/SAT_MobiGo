package com.shop.mobigo.models

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.ForeignKey.SET_DEFAULT

@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = Categories::class,
        parentColumns = arrayOf("cat_name"),
        childColumns = arrayOf("cat_value"),
        onUpdate = CASCADE,
        onDelete = SET_DEFAULT)))
data class Products(@PrimaryKey var id: Int,
                    var cat_value: String,
                    @ColumnInfo(name = "prod_name") var name: String,
                    var date_added: String,
                    @ColumnInfo(name="product_price")var price: Int,
                    @Ignore var variants: List<Variants>,
                    @Embedded var tax: Tax) {
    constructor() : this(0, "", "", "",0, emptyList(), Tax())
}