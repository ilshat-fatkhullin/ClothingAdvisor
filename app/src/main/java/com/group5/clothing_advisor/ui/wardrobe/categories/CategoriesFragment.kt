package com.group5.clothing_advisor.ui.wardrobe.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group5.clothing_advisor.databinding.FragmentCategoriesBinding
import com.group5.clothing_advisor.ui.adapters.CategoriesAdapter

class CategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private val viewModel: CategoriesViewModel by lazy {
        ViewModelProvider(this).get(CategoriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoriesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.categoryItems.adapter =
            CategoriesAdapter(
                CategoriesAdapter.OnClickListener {
                    viewModel.displayClothes(it)
                })
        viewModel.navigateToSelectedCategory.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(CategoriesFragmentDirections.actionShowClothes(it))
                viewModel.displayClothesComplete()
            }
        })
        return binding.root
    }
}