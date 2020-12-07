package com.group5.clothing_advisor.ui.wardrobe.clothes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.ClothesResponseItem

class ClothesViewModel(categoryResponseItem: CategoryResponseItem, app: Application) : ViewModel() {
    private val _selectedCategory = MutableLiveData<CategoryResponseItem>()

    val selectedCategory: LiveData<CategoryResponseItem>
        get() = _selectedCategory

    private val _clothesLoadingStatus = MutableLiveData<ApiStatus>()

    val clothesLoadingStatus: LiveData<ApiStatus>
        get() = _clothesLoadingStatus

    private val _clothes = MutableLiveData<List<ClothesResponseItem>>()

    val clothes: LiveData<List<ClothesResponseItem>>
        get() = _clothes

    init {
        _selectedCategory.value = categoryResponseItem
        _clothes.value = ArrayList()
        loadClothes()
    }

    private fun loadClothes() {
        if (_clothesLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid).collection("clothes").get()
            .addOnSuccessListener { result ->
                val list = ArrayList<ClothesResponseItem>()
                for (document in result) {
                    list.add(
                        ClothesResponseItem(
                            document.get("id") as String,
                            document.get("name") as String,
                            document.get("image_url") as String
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
}