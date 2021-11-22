package com.example.inventoryapp.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "item_name") val itemName: String,
    @ColumnInfo(name = "item_quantity") val itemQuantity: Int,
    @ColumnInfo(name = "item_supplier") val itemSupplier: String,
    @ColumnInfo(name = "item_price") val itemPrice: Int,
    @ColumnInfo(name = "item_image") val itemImage: Bitmap
    ): Parcelable
