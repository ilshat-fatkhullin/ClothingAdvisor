package com.group5.clothing_advisor.ui.wardrobe.clothes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.group5.clothing_advisor.data.CategoryResponseItem

class ClothesViewModelFactory(
    private val categoryResponseItem: CategoryResponseItem,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClothesViewModel::class.java)) {
            return ClothesViewModel(categoryResponseItem, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}