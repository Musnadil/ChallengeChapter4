package com.musnadil.challengechapter4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [User::class], version = 1)
abstract class StoreDatabase():RoomDatabase(){
    abstract fun storeDao() : UserDao
    companion object{
        private var INSTANCE: StoreDatabase? = null

        fun getInstance(context: Context):StoreDatabase?{
            if (INSTANCE == null){
                synchronized(StoreDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StoreDatabase::class.java,"Store.db"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
