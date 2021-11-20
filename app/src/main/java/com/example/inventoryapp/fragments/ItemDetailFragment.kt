package com.example.inventoryapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.InventoryApplication
import com.example.inventoryapp.InventoryViewModel
import com.example.inventoryapp.InventoryViewModelFactory
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {

    private val args by navArgs<ItemDetailFragmentArgs>()

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }

    lateinit var binding: FragmentItemDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_detail, container, false)
        with(binding) {
            //set all text fields to current item's values
            itemName.text = args.currentItem.itemName
            itemPrice.text = args.currentItem.itemPrice.toString()
            itemCount.text = args.currentItem.itemQuantity.toString()
            itemSupplier.text = args.currentItem.itemSupplier

            //logic for add item button
            addItem.setOnClickListener {
                val action = ItemDetailFragmentDirections.actionItemDetailFragmentToEditItemFragment(args.currentItem)
                findNavController().navigate(action)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteALl -> alertDialog()
        }
        return true
    }

    private fun alertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure you want to delete this item?")
        builder.setMessage("You will not be able to recover your data after deleting it")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.onDelete(args.currentItem)
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToItemListFragment()
            findNavController().navigate(action)
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(requireContext(),
                "Canceled", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}