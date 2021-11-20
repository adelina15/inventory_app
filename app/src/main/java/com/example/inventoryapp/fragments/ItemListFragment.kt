package com.example.inventoryapp.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.*
import com.example.inventoryapp.R.menu.menu
import com.example.inventoryapp.contract.Delegates
import com.example.inventoryapp.database.Item
import com.example.inventoryapp.databinding.FragmentItemListBinding

class ItemListFragment : Fragment(), Delegates.RecyclerItemClicked {

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }

    lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            addItem.setOnClickListener {
                val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment()
                findNavController().navigate(action)
            }

        }
        viewModel.allItems.observe(viewLifecycleOwner){
            binding.rcView.adapter = ItemAdapter(it, this)
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
        when(item.itemId){
            R.id.deleteALl -> alertDialog()
        }
        return true
    }

    private fun alertDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Are you sure you want to delete all items?")
        builder.setMessage("You will not be able to recover your data after deleting it")
        builder.setPositiveButton("Yes") { _, _ ->
            viewModel.onClear()
        }

        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(requireContext(),
                "Canceled", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    override fun onItemClick(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item)
        findNavController().navigate(action)
    }
}
