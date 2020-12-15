package com.group5.clothing_advisor.ui.wardrobe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.group5.clothing_advisor.R
import com.group5.clothing_advisor.ui.wardrobe.clothes_list.ClothesListFragment

class WardrobeFragment : Fragment() {

    companion object {
        fun newInstance() =
            ClothesListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wardrobe, container, false)
    }
}
