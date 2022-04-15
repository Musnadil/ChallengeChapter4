package com.musnadil.challengechapter4

import android.content.Context

class ItemRepository(private val context: Context){

    private val mDb = StoreDatabase.getInstance(context)
    fun getAllItem():List<Item>?{
        return mDb?.itemDao()?.getAllItem()
    }
    fun insertItem(item: Item):Long?{
        return mDb?.itemDao()?.insertItem(item)
    }
    fun updateItem(item: Item):Int?{
        return mDb?.itemDao()?.updateItem(item)
    }
    fun deleteItem(item: Item):Int?{
        return mDb?.itemDao()?.deleteItem(item)
    }
}