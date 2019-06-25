package com.shop.mobigo.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = Products::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("prod_id"),
        onUpdate = CASCADE,
        onDelete = CASCADE))
)
data class Variants(@PrimaryKey var id: Int,
                    var prod_id: Int,
                    var color: String,
                    var size: Int,
                    var price: Int) {
    @Ignore
    constructor() : this(0, 0, "", 0, 0)
}