package com.example.inventoryapp.view.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.inventoryapp.R
import com.example.inventoryapp.contract.ContractInterface
import com.example.inventoryapp.databinding.FragmentAddItemBinding
import com.example.inventoryapp.model.Item
import com.example.inventoryapp.presenter.Presenter
import com.example.inventoryapp.repository.Repository
import com.example.inventoryapp.view.InventoryApplication

class AddItemFragment : Fragment(), ContractInterface.View {

    lateinit var item: Item
    lateinit var imageUrl: String
    private val presenter by lazy { Presenter(Repository((activity?.application as InventoryApplication).database.itemDao())) }

    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false)
        return binding.root
    }

    // Returns true if the EditTexts are not empty
    override fun isEntryValid(): Boolean {
        return presenter.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
            binding.itemSupplier.text.toString())
    }

    //Inserts the new Item into database and navigates up to list fragment.
    override fun addNewItem() {
        if (isEntryValid()) {
            val itemToInsert = Item(0,
                binding.itemName.text.toString(),
                binding.itemCount.text.toString().toInt(),
                binding.itemSupplier.text.toString(),
                binding.itemPrice.text.toString().toInt(),
                imageUrl
            )
            presenter.insert(itemToInsert)
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
        else Toast.makeText(requireContext(),
            "Entry can not be empty", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(view)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
        binding.uploadButton.setOnClickListener{
            openGalleryForImage()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUrl = data?.data.toString()
            binding.image.setImageURI(data?.data)
        }
    }

    override fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        presenter.detachView()
    }
}