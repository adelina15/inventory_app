package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventoryapp.*
import com.example.inventoryapp.databinding.FragmentItemListBinding

class ItemListFragment : Fragment() {
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }
    lateinit var binding: FragmentItemListBinding
    private val adapter by lazy{ ItemAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        binding.apply {
            rcView.adapter = adapter
            rcView.layoutManager = LinearLayoutManager(requireContext())
            editItem.setOnClickListener {
                val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment()
                findNavController().navigate(action)
            }

        }
        viewModel.allItems.observe(viewLifecycleOwner){
            it.let { adapter.submitList(it) }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
