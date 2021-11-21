//package com.example.inventoryapp
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import com.example.inventoryapp.model.database.Item
//import com.example.inventoryapp.model.database.ItemDao
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
///**
// * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
// *
// */
//class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {
//
//    val allItems: LiveData<List<Item>> = itemDao.getAll()
//
//    //Launching a new coroutine to delete all items in a non-blocking way
//    private suspend fun clear() {
//        withContext(Dispatchers.IO) {
//            itemDao.clear()
//        }
//    }
//
//    fun onClear() {
//        viewModelScope.launch {
//            // Clear the database table.
//            clear()
//        }
//    }
//
//    private suspend fun delete(item: Item) {
//        withContext(Dispatchers.IO) {
//            itemDao.delete(item)
//        }
//    }
//
//    fun onDelete(item: Item){
//        viewModelScope.launch {
//            // Clear the database table.
//            delete(item)
//        }
//    }
//
//
//    //Inserts the new Item into database.
//    fun addNewItem(itemName: String, itemPrice: String, itemCount: String, itemSupplier: String, itemImage: String) {
//        val newItem = getNewItemEntry(itemName, itemPrice, itemCount, itemSupplier, itemImage)
//        insertItem(newItem)
//    }
//
//    //Launching a new coroutine to insert an item in a non-blocking way
//    private fun insertItem(item: Item) {
//        viewModelScope.launch {
//            itemDao.insert(item)
//        }
//    }
//
//    fun update(item: Item){
//        viewModelScope.launch {
//            itemDao.update(item)
//        }
//    }
//
//    //Returns true if the EditTexts are not empty
//    fun isEntryValid(
//        itemName: String,
//        itemPrice: String,
//        itemCount: String,
//        itemSupplier: String
//    ): Boolean {
//        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank() || itemSupplier.isBlank()) {
//            return false
//        }
//        return true
//    }
//
//    /* Returns an instance of the [Item] entity class with the item info entered by the user.
//       This will be used to add a new entry to the Inventory database. */
//    private fun getNewItemEntry(
//        itemName: String,
//        itemPrice: String,
//        itemCount: String,
//        itemSupplier: String,
//        itemImage: String
//    ): Item {
//        return Item(
//            itemName = itemName,
//            itemPrice = itemPrice.toInt(),
//            itemQuantity = itemCount.toInt(),
//            itemSupplier = itemSupplier,
//            itemImage = itemImage
//        )
//    }
//}
//
///**
// * Factory class to instantiate the [ViewModel] instance.
// */
//class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return InventoryViewModel(itemDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}