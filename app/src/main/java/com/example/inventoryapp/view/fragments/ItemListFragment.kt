package com.example.inventoryapp.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventoryapp.*
import com.example.inventoryapp.contract.Delegates
import com.example.inventoryapp.model.Item
import com.example.inventoryapp.databinding.FragmentItemListBinding
import com.example.inventoryapp.presenter.Presenter
import com.example.inventoryapp.repository.Repository
import com.example.inventoryapp.view.InventoryApplication
import com.example.inventoryapp.view.ItemAdapter

class ItemListFragment : Fragment(), Delegates.RecyclerItemClicked {

    private val presenter by lazy { Presenter(Repository((activity?.application as InventoryApplication).database.itemDao())) }
    private var _binding: FragmentItemListBinding? = null
    private val binding: FragmentItemListBinding
    get() {
        return _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_list, container, false)
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            addItem.setOnClickListener {
                val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment()
                findNavController().navigate(action)
            }
        }
        presenter.getALl().observe(viewLifecycleOwner) {
            binding.rcView.adapter = ItemAdapter(it, this)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        presenter.attachView(view)

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
        builder.setTitle("Are you sure you want to delete all items?")
        builder.setMessage("You will not be able to recover your data after deleting it")
        builder.setPositiveButton("Yes") { _, _ ->
            presenter.clear()
        }

        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Canceled", Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    override fun onItemClick(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item)
        findNavController().navigate(action)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        presenter.detachView()
//    }
}
