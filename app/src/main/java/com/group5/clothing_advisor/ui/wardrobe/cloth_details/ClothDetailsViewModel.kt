package com.group5.clothing_advisor.ui.wardrobe.cloth_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.group5.clothing_advisor.data.ClothResponseItem

class ClothDetailsViewModel(clothResponseItem: ClothResponseItem) : ViewModel() {
    private val _selectedCloth = MutableLiveData<ClothResponseItem>()

    val selectedCloth: LiveData<ClothResponseItem>
        get() = _selectedCloth

    private val _navigateBack = MutableLiveData<Boolean>()

    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    private val _showError = MutableLiveData<Boolean>()

    val showError: LiveData<Boolean>
        get() = _showError

    init {
        _selectedCloth.value = clothResponseItem
    }

    fun removeCloth() {
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("clothes")
            .document(_selectedCloth.value!!.id)
            .delete()
            .addOnSuccessListener {
                _navigateBack.postValue(true)
            }
            .addOnFailureListener {
                _showError.postValue(true)
            }
    }

    fun navigatedBack() {
        _navigateBack.postValue(false)
    }

    fun errorShown() {
        _showError.postValue(false)
    }
}
