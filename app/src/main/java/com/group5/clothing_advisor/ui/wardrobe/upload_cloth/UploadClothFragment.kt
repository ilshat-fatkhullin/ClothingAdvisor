package com.group5.clothing_advisor.ui.wardrobe.upload_cloth

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group5.clothing_advisor.R
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.TemperatureResponseItem
import com.group5.clothing_advisor.databinding.FragmentClothesListBinding
import com.group5.clothing_advisor.databinding.FragmentUploadClothBinding
import com.group5.clothing_advisor.ui.adapters.ClothesAdapter
import com.group5.clothing_advisor.ui.wardrobe.clothes_list.ClothesListFragmentDirections
import com.group5.clothing_advisor.ui.wardrobe.clothes_list.ClothesListViewModel
import java.io.IOException

class UploadClothFragment : Fragment() {

    private lateinit var viewModel: UploadClothViewModel

    private lateinit var binding: FragmentUploadClothBinding

    private val PICK_IMAGE_REQUEST = 71

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadClothBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(UploadClothViewModel::class.java)
        binding.viewModel = viewModel
        binding.categoriesSpinner.adapter = createAdapter()
        binding.categoriesSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectCategory(position)
                }
            }
        binding.temperatureSpinner.adapter = createAdapter()
        binding.temperatureSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {
                        viewModel.selectTemperature(position)
                    }
                }
        binding.uploadPhoto.setOnClickListener {
            launchGallery()
        }
        binding.add.setOnClickListener {
            viewModel.uploadImage()
        }
        viewModel.categories.observe(this.viewLifecycleOwner, Observer {
            setCategories(it)
        })
        viewModel.temperatures.observe(this.viewLifecycleOwner, Observer {
            setTemperatures(it)
        })
        viewModel.navigateBack.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
                viewModel.navigatedBack()
            }
        })
        viewModel.showError.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG)
                viewModel.errorShown()
            }
        })

        return binding.root
    }

    private fun createAdapter(): ArrayAdapter<String> {
        val adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            ArrayList(listOf<String>())
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun setCategories(categories: List<CategoryResponseItem>) {
        val adapter = binding.categoriesSpinner.adapter as ArrayAdapter<String>
        adapter.clear()
        for (category in categories) {
            adapter.add(category.name)
        }
    }

    private fun setTemperatures(temps: List<TemperatureResponseItem>) {
        val adapter = binding.temperatureSpinner.adapter as ArrayAdapter<String>
        adapter.clear()
        for (temp in temps) {
            adapter.add(temp.name)
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            val pathToImage = data.data
            try {
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, pathToImage)
                viewModel.setPathToImage(pathToImage!!)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}