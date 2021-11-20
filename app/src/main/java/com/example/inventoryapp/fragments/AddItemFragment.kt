package com.example.inventoryapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.inventoryapp.InventoryApplication
import com.example.inventoryapp.InventoryViewModel
import com.example.inventoryapp.InventoryViewModelFactory
import com.example.inventoryapp.R
import com.example.inventoryapp.database.Item
import com.example.inventoryapp.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {

    lateinit var item: Item
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).database.itemDao())
    }
    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false)
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString(),
            binding.itemSupplier.text.toString()
        )
    }

    /**
     * Inserts the new Item into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString(),
                binding.itemSupplier.text.toString(),
//                binding.image.toBitmap()
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
        else Toast.makeText(requireContext(),
            "Entry can not be empty", Toast.LENGTH_SHORT).show()
    }

    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit item  or add new item.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            binding.image.setImageURI(data?.data)
//            viewModel.getBitmap(data?.data)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

//    private suspend fun getBitmap(data: Uri?): Bitmap {
//        val loading = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data(data)
//            .build()
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }


    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}