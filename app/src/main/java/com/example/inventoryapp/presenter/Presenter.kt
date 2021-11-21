package com.example.inventoryapp.presenter

import android.view.View
import androidx.lifecycle.LiveData
import com.example.inventoryapp.contract.ContractInterface
import com.example.inventoryapp.model.Item
import com.example.inventoryapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Presenter(private val repository: Repository): ContractInterface.Presenter {

    private var _view: View? = null

    override fun insert(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(item)
        }
    }

    override fun getALl(): LiveData<List<Item>> {
        return repository.getAll()
    }

    override fun delete(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(item)
        }
    }

    override fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.clear()
        }
    }

    override fun update(item: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.update(item)
        }
    }

    override fun attachView(view: View) {
        this._view = view
    }

    override fun detachView() {
        _view = null
    }

    override fun isEntryValid(
        itemName: String,
        itemPrice: String,
        itemCount: String,
        itemSupplier: String
    ): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank() || itemSupplier.isBlank()) {
            return false
        }
        return true
    }
}