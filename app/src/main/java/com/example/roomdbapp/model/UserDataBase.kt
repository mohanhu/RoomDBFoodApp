package com.example.roomdbapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase(){

    abstract fun userDao():UserDao

    companion object{
        @Volatile
        private var INSTANCE :UserDataBase?=null
        fun getDataBase(context: Context):UserDataBase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,UserDataBase::class.java,"user_table").build()
                INSTANCE=instance
                return instance
            }
        }
    }
}