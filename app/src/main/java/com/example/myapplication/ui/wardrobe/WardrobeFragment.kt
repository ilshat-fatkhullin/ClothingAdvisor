package com.example.myapplication.ui.wardrobe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.ui.wardrobe.clothes.ClothesFragment

class WardrobeFragment : Fragment() {

    companion object {
        fun newInstance() = ClothesFragment()
    }

    private lateinit var viewModel: WardrobeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wardrobe, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WardrobeViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
