package com.group5.clothing_advisor.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group5.clothing_advisor.databinding.FragmentHomeBinding
import com.group5.clothing_advisor.ui.adapters.ClothesAdapter
import com.group5.clothing_advisor.ui.wardrobe.clothes_list.ClothesListFragmentDirections

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.recommendationGrid.adapter =
            ClothesAdapter(
                ClothesAdapter.OnClickListener {
                })

        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadRecommendation()
            if (binding.swiperefresh.isRefreshing) {
                binding.swiperefresh.isRefreshing = false;
            }
        }

        return binding.root
    }
}