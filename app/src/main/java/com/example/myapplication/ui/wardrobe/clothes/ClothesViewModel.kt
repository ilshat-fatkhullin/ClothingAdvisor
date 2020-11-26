package com.example.myapplication.ui.wardrobe.clothes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.network.CategoryResponseItem

class ClothesViewModel(categoryResponseItem: CategoryResponseItem, app: Application) : ViewModel() {
    private val _selectedCategory = MutableLiveData<CategoryResponseItem>()

    val selectedProperty: LiveData<CategoryResponseItem>
        get() = _selectedCategory

    init {
        _selectedCategory.value = categoryResponseItem
    }
}