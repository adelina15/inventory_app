package com.example.inventoryapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.R
import com.example.inventoryapp.model.Item
import com.example.inventoryapp.databinding.FragmentEditItemBinding
import com.example.inventoryapp.presenter.Presenter
import com.example.inventoryapp.repository.Repository
import com.example.inventoryapp.view.InventoryApplication


class EditItemFragment : Fragment() {
    private val args by navArgs<EditItemFragmentArgs>()
    lateinit var binding: FragmentEditItemBinding

    private val presenter by lazy { Presenter(Repository((activity?.application as InventoryApplication).database.itemDao())) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_item, container, false)
        with(binding){
            itemName.setText(args.currentItem.itemName)
            itemCount.setText(args.currentItem.itemQuantity.toString())
            itemPrice.setText(args.currentItem.itemPrice.toString())
            itemSupplier.setText(args.currentItem.itemSupplier)
            saveAction.setOnClickListener{
                updateItem()
            }
        }
        return binding.root
    }

    private fun updateItem(){
        val name = binding.itemName.text.toString()
        val price = Integer.parseInt(binding.itemPrice.text.toString())
        val quantity = Integer.parseInt(binding.itemCount.text.toString())
        val supplier = binding.itemSupplier.text.toString()
        val image = args.currentItem.itemImage
        if (isEntryValid()) {
            val updatedItem = Item(args.currentItem.id, name, quantity, supplier, price, image)
            presenter.update(updatedItem)
            val action = EditItemFragmentDirections.actionEditItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
        else Toast.makeText(requireContext(),
            "Entry can not be empty", Toast.LENGTH_SHORT).show()
    }

    private fun isEntryValid(): Boolean {
        return presenter.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
            binding.itemSupplier.text.toString()
        )
    }
}