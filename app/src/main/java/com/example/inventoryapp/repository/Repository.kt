package com.example.inventoryapp.repository

import androidx.lifecycle.LiveData
import com.example.inventoryapp.model.Item
import com.example.inventoryapp.model.database.ItemDao

class Repository(private val itemDao: ItemDao) {

    fun getAll(): LiveData<List<Item>> {
        return itemDao.getAll()
    }

    suspend fun insert(item: Item) {
        itemDao.insert(item)
    }

    suspend fun delete(item: Item) {
        itemDao.delete(item)
    }

    suspend fun clear() {
        itemDao.clear()
    }

    suspend fun update(item: Item) {
        itemDao.update(item)
    }

}