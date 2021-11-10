package com.example.inventoryapp.contract

interface RecyclerViewContract {
    interface AdapterViewHolder{
        fun setName(name: String)
        fun setPrice(price: Int)
        fun setQuantity(quantity: Int)
        fun setSupplier(supplier: String)
    }
}
