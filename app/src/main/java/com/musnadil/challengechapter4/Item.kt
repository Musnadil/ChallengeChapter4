package com.musnadil.challengechapter4

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "item_name") var item_name : String,
    @ColumnInfo(name = "purchase_price") var purchase_price : Int,
    @ColumnInfo(name = "selling_price") var selling_price : Int
)
