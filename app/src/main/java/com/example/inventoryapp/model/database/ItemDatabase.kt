package com.example.inventoryapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventoryapp.model.Item


@Database(entities = [Item::class], version = 3)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "item_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}