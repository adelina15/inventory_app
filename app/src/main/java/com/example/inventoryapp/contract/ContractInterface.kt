package com.example.inventoryapp.contract

import androidx.lifecycle.LiveData
import com.example.inventoryapp.model.Item

interface ContractInterface {
    interface View {
        fun addNewItem()
        fun isEntryValid(): Boolean
        fun openGalleryForImage()
    }

    interface Presenter {
        fun insert(item: Item)
        fun getALl():LiveData<List<Item>>
        fun delete(item: Item)
        fun clear()
        fun update(item: Item)
        fun attachView(view: android.view.View)
        fun detachView()
        fun isEntryValid(itemName: String, itemPrice: String, itemCount: String, itemSupplier: String): Boolean
    }

}