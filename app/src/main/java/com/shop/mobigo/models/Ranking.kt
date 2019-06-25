package com.shop.mobigo.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity
data class Ranking(@PrimaryKey var ranking: String,
                   @Ignore var products: List<RankingProducts>) {
    constructor() : this("", emptyList())
}