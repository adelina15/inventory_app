package com.example.inventoryapp

import android.app.Application
import com.example.inventoryapp.database.ItemDatabase


class InventoryApplication: Application() {
    val database: ItemDatabase by lazy { ItemDatabase.getInstance(this) }
}