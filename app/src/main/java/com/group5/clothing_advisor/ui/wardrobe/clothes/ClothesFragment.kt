package com.group5.clothing_advisor.ui.wardrobe.clothes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.group5.clothing_advisor.databinding.FragmentClothesBinding
import com.group5.clothing_advisor.ui.adapters.ClothesAdapter

class ClothesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentClothesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val categoryResponseItem = ClothesFragmentArgs.fromBundle(requireArguments()).selectedCategory
        val viewModelFactory = ClothesViewModelFactory(categoryResponseItem, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(ClothesViewModel::class.java)
        binding.clothesGrid.adapter =
            ClothesAdapter(
                ClothesAdapter.OnClickListener {

                })
        return binding.root
    }
}
