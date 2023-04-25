package com.example.roomdbapp.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun insertAllData(user: User) = userDao.insertTask(user)

    suspend fun search(search: String): LiveData<List<User>> {
        return userDao.search(search)
    }

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun update(user: User) = userDao.update(user)

    suspend fun delete(user: User) = userDao.delete(user)

    fun deleteAll() = userDao.deleteAll()

}