package com.example.inventoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.inventoryapp.contract.Delegates
import com.example.inventoryapp.databinding.ItemsBinding
import com.example.inventoryapp.database.Item

class ItemAdapter(private val items: List<Item>, private val itemClicker: Delegates.RecyclerItemClicked) :
    ListAdapter<Item, ItemAdapter.ItemHolder>(ItemsComparator()) {

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemsBinding.bind(view)

        fun bind(item: Item) = with(binding) {
            name.text = item.itemName
            price.text = item.itemPrice.toString()
            quantity.text = item.itemQuantity.toString()
            supplier.text = item.itemSupplier
//            itemImage.load(item.itemImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.layout.setOnClickListener {
            itemClicker.onItemClick(items[position])
        }
    }

    override fun getItemCount() = items.size

    class ItemsComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id

        }
    }
}
