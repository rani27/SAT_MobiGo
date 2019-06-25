package com.shop.mobigo.models

import android.arch.persistence.room.Ignore


data class Tax(var name: String,
               var value: Float){@Ignore constructor() : this( "",0.0F)}