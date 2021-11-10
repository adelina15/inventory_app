package com.example.inventoryapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.database.Item
import com.example.inventoryapp.database.ItemDao
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getAll()
    /**
     * Inserts the new Item into database.
     */
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String, itemSupplier: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount, itemSupplier)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String, itemSupplier: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank() || itemSupplier.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(
        itemName: String,
        itemPrice: String,
        itemCount: String,
        itemSupplier: String
    ): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toInt(),
            itemQuantity = itemCount.toInt(),
            itemSupplier = itemSupplier
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}