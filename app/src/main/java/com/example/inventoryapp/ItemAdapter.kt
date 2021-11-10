package com.example.inventoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.databinding.ItemsBinding
import com.example.inventoryapp.contract.RecyclerViewContract
import com.example.inventoryapp.database.Item

class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemHolder>(ItemsComparator()) {

    private val presenter = AdapterPresenter()

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view),
        RecyclerViewContract.AdapterViewHolder {
        private val binding = ItemsBinding.bind(view)

        override fun setName(name: String) {
            binding.name.text = name
        }
        override fun setPrice(price: Int) {
            binding.price.text = price.toString()
        }
        override fun setQuantity(quantity: Int) {
            binding.quantity.text = quantity.toString()
        }
        override fun setSupplier(supplier: String) {
            binding.supplier.text = supplier
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        presenter.onBind(position, holder)
    }

    override fun getItemCount() = presenter.getCount()

    class ItemsComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id

        }
    }
}
