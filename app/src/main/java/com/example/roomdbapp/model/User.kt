package com.example.roomdbapp.model

import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val first:String,
    val last:String,
    val age:Int,
    val image:Bitmap,
    @Embedded
    val address:Address
):Serializable

data class Address(
    val streetName:String,
    val  streetNumber:Int
):Serializable