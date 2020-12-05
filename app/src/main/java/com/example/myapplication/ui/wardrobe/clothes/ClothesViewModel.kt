package com.example.myapplication.ui.wardrobe.clothes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.Api
import com.example.myapplication.network.ApiStatus
import com.example.myapplication.network.CategoryResponseItem
import com.example.myapplication.network.ClothesResponseItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ClothesViewModel(categoryResponseItem: CategoryResponseItem, app: Application) : ViewModel() {
    private val _selectedCategory = MutableLiveData<CategoryResponseItem>()

    val selectedProperty: LiveData<CategoryResponseItem>
        get() = _selectedCategory

    private val _clothesLoadingStatus = MutableLiveData<ApiStatus>()

    val clothesLoadingStatus: LiveData<ApiStatus>
        get() = _clothesLoadingStatus

    private val _clothes = MutableLiveData<List<ClothesResponseItem>>()

    val clothes: LiveData<List<ClothesResponseItem>>
        get() = _clothes

    private val _apiKey = MutableLiveData<String>()

    private val _page = MutableLiveData<Int>()

    private val _limit = MutableLiveData<Int>()

    private val _imageLoadingJob = MutableLiveData<Job>()

    init {
        _selectedCategory.value = categoryResponseItem
        loadMoreClothes()
    }

    private fun loadMoreClothes() {
        if (_clothesLoadingStatus.value == ApiStatus.LOADING)
            return

//        _imageLoadingJob.value = viewModelScope.launch {
//            _clothesLoadingStatus.value = ApiStatus.LOADING
//            try {
//                val result = ArrayList<ClothesResponseItem>(_clothes.value!!)
//                result.addAll(
//                    Api.retrofitService.getClothes(
//                    )
//                )
//                _clothes.value = result
//                _clothesLoadingStatus.value = ApiStatus.DONE
//                _page.value = _page.value!! + 1
//            } catch (e: Exception) {
//                _clothesLoadingStatus.value = ApiStatus.ERROR
//            }
//        }
    }

    fun onScrolledUntilEnd() {
        loadMoreClothes()
    }
}