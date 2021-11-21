package com.example.inventoryapp.view

import android.app.Application
import com.example.inventoryapp.model.database.ItemDatabase


class InventoryApplication: Application() {
    val database: ItemDatabase by lazy { ItemDatabase.getInstance(this) }
}