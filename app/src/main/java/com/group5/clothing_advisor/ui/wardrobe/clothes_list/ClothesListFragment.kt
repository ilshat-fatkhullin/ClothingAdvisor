package com.group5.clothing_advisor.ui.wardrobe.clothes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.databinding.FragmentClothesListBinding
import com.group5.clothing_advisor.ui.adapters.ClothesAdapter


class ClothesListFragment : Fragment() {

    private lateinit var viewModel: ClothesListViewModel

    private lateinit var binding: FragmentClothesListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClothesListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ClothesListViewModel::class.java)
        binding.viewModel = viewModel

        binding.clothesGrid.adapter =
            ClothesAdapter(
                ClothesAdapter.OnClickListener {
                    findNavController().navigate(ClothesListFragmentDirections.actionShowCloth(it))
                })

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

        binding.fab.setOnClickListener {
            findNavController().navigate(ClothesListFragmentDirections.actionUploadCloth())
        }

        viewModel.categories.observe(this.viewLifecycleOwner, Observer {
            setCategories(it)
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
        adapter.add(getString(com.group5.clothing_advisor.R.string.any))
        for (category in categories) {
            adapter.add(category.name)
        }
    }
}
