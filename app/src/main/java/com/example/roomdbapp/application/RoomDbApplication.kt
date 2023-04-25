package com.example.roomdbapp.application

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber

class RoomDbApplication :Application(){
    override fun onCreate() {
        super.onCreate()
       if(BuildConfig.DEBUG){
           Timber.plant(Timber.DebugTree())
       }
    }

}