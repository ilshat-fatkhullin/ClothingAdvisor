package com.group5.clothing_advisor.ui.wardrobe.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {
    val categories: LiveData<List<CategoryResponseItem>>
        get() = _categories

    private val _categories = MutableLiveData<List<CategoryResponseItem>>()

    private val _categoriesLoadingStatus = MutableLiveData<ApiStatus>()

    private val _navigateToSelectedCategory = MutableLiveData<CategoryResponseItem>()

    val navigateToSelectedCategory: LiveData<CategoryResponseItem>
        get() = _navigateToSelectedCategory


    init {
        loadCategories()
    }

    private fun loadCategories() {
        if (_categoriesLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("categories").get()
        .addOnSuccessListener {result ->
            val list = ArrayList<CategoryResponseItem>()
            for (document in result)
            {
                list.add(
                    CategoryResponseItem(
                        document.get("name") as String
                    )
                )
            }
            _categories.postValue(list)
            _categoriesLoadingStatus.value = ApiStatus.DONE
        }.addOnFailureListener {result ->
            _categories.postValue(ArrayList())
            _categoriesLoadingStatus.value = ApiStatus.ERROR
        }

        viewModelScope.launch {
            try {

                _categoriesLoadingStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _categories.value = null
                _categoriesLoadingStatus.value = ApiStatus.ERROR
            }
        }
    }

    fun displayClothes(categoryResponseItem: CategoryResponseItem) {
        _navigateToSelectedCategory.value = categoryResponseItem
    }

    fun displayClothesComplete() {
        _navigateToSelectedCategory.value = null
    }
}