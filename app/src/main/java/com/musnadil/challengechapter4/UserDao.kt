package com.musnadil.challengechapter4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE username like :username and password like :password")
    fun userCheck(username: String, password: String):Boolean

    @Insert(onConflict = REPLACE)
    fun addUser(user: User): Long
}