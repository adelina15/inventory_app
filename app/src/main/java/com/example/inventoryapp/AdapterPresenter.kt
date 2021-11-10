package com.example.inventoryapp

import com.example.inventoryapp.database.Item

class AdapterPresenter {
    var data = listOf<Item>()

    fun onBind(position: Int, holder: ItemAdapter.ItemHolder) {
        val item = data[position]
        holder.setName(item.itemName)
        holder.setPrice(item.itemPrice)
        holder.setQuantity(item.itemQuantity)
        holder.setSupplier(item.itemSupplier)
    }

    fun getCount(): Int {
        return data.size
    }

}