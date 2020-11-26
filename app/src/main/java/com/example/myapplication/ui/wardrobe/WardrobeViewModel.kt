package com.example.myapplication.ui.wardrobe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.Api
import com.example.myapplication.network.ApiStatus
import com.example.myapplication.network.CategoryResponseItem
import kotlinx.coroutines.launch

class WardrobeViewModel : ViewModel() {
    val categoryItems: LiveData<List<CategoryResponseItem>>
        get() = _categoryItems

    private val _categoryItems = MutableLiveData<List<CategoryResponseItem>>()

    private val _categoriesLoadingStatus = MutableLiveData<ApiStatus>()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        if (_categoriesLoadingStatus.value == ApiStatus.LOADING)
            return

        viewModelScope.launch {
            try {
                //_categoryItems.value = Api.retrofitService.getCategories()
                val list = ArrayList<CategoryResponseItem>()
                list.add(CategoryResponseItem(0, "Upper part"))
                list.add(CategoryResponseItem(0, "Middle part"))
                list.add(CategoryResponseItem(0, "Lower part"))
                _categoryItems.value = list
                _categoriesLoadingStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _categoryItems.value = null
                _categoriesLoadingStatus.value = ApiStatus.ERROR
            }
        }
    }
}
