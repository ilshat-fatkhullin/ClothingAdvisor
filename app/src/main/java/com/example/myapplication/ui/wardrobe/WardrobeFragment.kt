package com.example.myapplication.ui.wardrobe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentWardrobeBinding
import com.example.myapplication.ui.adapters.CategoriesAdapter

class WardrobeFragment : Fragment() {

    companion object {
        fun newInstance() = WardrobeFragment()
    }

    private val viewModel: WardrobeViewModel by lazy {
        ViewModelProvider(this).get(WardrobeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWardrobeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.categoryItems.adapter =
            CategoriesAdapter(
                CategoriesAdapter.OnClickListener {

                })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}