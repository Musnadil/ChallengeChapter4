package com.musnadil.challengechapter4

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAllItem():List<Item>

    @Insert(onConflict = REPLACE)
    fun insertItem(item: Item):Long

    @Update
    fun updateItem(item: Item):Int

    @Delete
    fun deleteItem(item: Item):Int

}