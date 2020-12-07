package com.group5.clothing_advisor.ui.wardrobe.cloth_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group5.clothing_advisor.data.ClothResponseItem

class ClothDetailsViewModelFactory(
    private val theCatSearchResponseItem: ClothResponseItem
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothDetailsViewModel::class.java)) {
            return ClothDetailsViewModel(theCatSearchResponseItem) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}