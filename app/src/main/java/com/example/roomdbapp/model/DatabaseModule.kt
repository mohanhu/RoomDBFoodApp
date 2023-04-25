package com.example.roomdbapp.model

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

class DatabaseModule {
    @Module
    @InstallIn(ApplicationComponent::class)
    object DatabaseModule {

        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ) = Room.databaseBuilder(
            context,
            UserDataBase::class.java,
            "user_table"
        ).build()

        @Singleton
        @Provides
        fun provideDao(database: UserDataBase) = database.userDao()

    }

}