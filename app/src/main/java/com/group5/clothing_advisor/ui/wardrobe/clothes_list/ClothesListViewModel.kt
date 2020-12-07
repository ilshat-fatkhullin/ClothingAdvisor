package com.group5.clothing_advisor.ui.wardrobe.clothes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.ClothResponseItem
import kotlinx.coroutines.launch

class ClothesListViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<CategoryResponseItem>>()

    val categories: LiveData<List<CategoryResponseItem>>
        get() = _categories

    private val _selectedCategory = MutableLiveData<CategoryResponseItem>()

    val selectedCategory: LiveData<CategoryResponseItem>
        get() = _selectedCategory

    private val _categoriesLoadingStatus = MutableLiveData<ApiStatus>()

    private val _clothesLoadingStatus = MutableLiveData<ApiStatus>()

    val clothesLoadingStatus: LiveData<ApiStatus>
        get() = _clothesLoadingStatus

    private val _clothes = MutableLiveData<List<ClothResponseItem>>()

    val clothes: LiveData<List<ClothResponseItem>>
        get() = _clothes

    init {
        _clothes.value = ArrayList()
        loadCategories()
    }

    fun selectCategory(position: Int) {
        if (position == 0) {
            _selectedCategory.value = null
        } else {
            _selectedCategory.value = _categories.value?.get(position - 1)
        }
        loadClothes()
    }

    private fun loadClothes() {
        if (_clothesLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("clothes").get()
            .addOnSuccessListener { result ->
                val list = ArrayList<ClothResponseItem>()
                for (document in result) {
                    if (_selectedCategory.value != null && _selectedCategory.value!!.id != document.getString("category_id"))
                        continue
                    list.add(
                        ClothResponseItem(
                            document.id,
                            document.getString("image_url")!!
                        )
                    )
                }
                _clothes.postValue(list)
                _clothesLoadingStatus.value = ApiStatus.DONE
            }
            .addOnFailureListener {
                _clothesLoadingStatus.value = ApiStatus.ERROR
            }
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
                            document.id,
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
}