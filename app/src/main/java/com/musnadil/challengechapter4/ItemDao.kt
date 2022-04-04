package com.musnadil.challengechapter4

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAllItem():List<Item>

    @Insert(onConflict = REPLACE)
    fun insertItem(student: Item):Long

    @Update
    fun updateItem(student: Item):Int

    @Delete
    fun deleteItem(student: Item):Int

}