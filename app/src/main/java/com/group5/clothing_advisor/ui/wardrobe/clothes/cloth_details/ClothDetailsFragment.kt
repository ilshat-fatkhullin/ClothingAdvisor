package com.group5.clothing_advisor.ui.wardrobe.clothes.cloth_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group5.clothing_advisor.R

class ClothDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ClothDetailsFragment()
    }

    private lateinit var viewModel: ClothDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cloth_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ClothDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}