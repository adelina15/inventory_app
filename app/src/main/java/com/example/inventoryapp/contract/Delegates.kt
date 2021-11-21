package com.example.inventoryapp.contract

import com.example.inventoryapp.model.Item

interface Delegates {
    interface RecyclerItemClicked{
        fun onItemClick(item: Item)
    }
}
