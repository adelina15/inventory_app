package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.InventoryApplication
import com.example.inventoryapp.InventoryViewModel
import com.example.inventoryapp.InventoryViewModelFactory
import com.example.inventoryapp.R
import com.example.inventoryapp.database.Item
import com.example.inventoryapp.databinding.FragmentEditItemBinding


class EditItemFragment : Fragment() {
    private val args by navArgs<EditItemFragmentArgs>()
    lateinit var binding: FragmentEditItemBinding

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }

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
        if (isEntryValid()) {
            val updatedItem = Item(args.currentItem.id, name, quantity, supplier, price)
            viewModel.update(updatedItem)
            val action = EditItemFragmentDirections.actionEditItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
        else Toast.makeText(requireContext(),
            "Entry can not be empty", Toast.LENGTH_SHORT).show()
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
            binding.itemSupplier.text.toString()
        )
    }
}