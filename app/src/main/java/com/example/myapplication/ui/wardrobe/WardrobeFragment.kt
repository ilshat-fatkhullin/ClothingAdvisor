package com.example.myapplication.ui.wardrobe

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R

class WardrobeFragment : Fragment() {

    companion object {
        fun newInstance() = WardrobeFragment()
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