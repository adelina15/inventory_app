package com.example.inventoryapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Update
    suspend fun update(item: Item)

    @Query("SELECT * FROM item ORDER BY id DESC")
    fun getAll(): LiveData<List<Item>>

    @Query("DELETE FROM item")
    fun clear()

}