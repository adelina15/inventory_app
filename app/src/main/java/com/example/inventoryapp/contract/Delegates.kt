package com.example.inventoryapp.contract

import com.example.inventoryapp.database.Item

interface Delegates {
    interface RecyclerItemClicked{
        fun onItemClick(item: Item)
    }
}
