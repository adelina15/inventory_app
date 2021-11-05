package com.example.inventoryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.databinding.ItemsBinding
import com.example.inventoryapp.contract.RecyclerViewContract

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    val presenter = AdapterPresenter()

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view),
        RecyclerViewContract.AdapterViewHolder {
        private val binding = ItemsBinding.bind(view)

        override fun setName(name: String) {
            binding.name.text = name
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
}
