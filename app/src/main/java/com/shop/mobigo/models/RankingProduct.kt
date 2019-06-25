package com.shop.mobigo.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = Ranking::class,
        parentColumns = arrayOf("ranking"),
        childColumns = arrayOf("ranking_name"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE)))
data class RankingProducts(@PrimaryKey(autoGenerate = true) var item_id: Int,
                           @ColumnInfo(name = "prod_id") var id: Int,
                           var ranking_name: String,
                           @SerializedName(value = "count", alternate = arrayOf("view_count", "order_count", "shares")) var count: Int)