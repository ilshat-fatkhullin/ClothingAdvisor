package com.group5.clothing_advisor.ui.wardrobe.upload_cloth

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.TemperatureResponseItem
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class UploadClothViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<CategoryResponseItem>>()

    val categories: LiveData<List<CategoryResponseItem>>
        get() = _categories

    private val _selectedCategory = MutableLiveData<CategoryResponseItem>()

    private val _categoriesLoadingStatus = MutableLiveData<ApiStatus>()

    private val _temperatures = MutableLiveData<List<TemperatureResponseItem>>()

    val temperatures: LiveData<List<TemperatureResponseItem>>
        get() = _temperatures

    private val _selectedTemperature = MutableLiveData<TemperatureResponseItem>()

    private val _temperaturesLoadingStatus = MutableLiveData<ApiStatus>()

    private val _pathToImage = MutableLiveData<Uri>()

    val pathToImage: LiveData<Uri>
        get() = _pathToImage

    private val _isValid = MutableLiveData<Boolean>()

    val isValid: LiveData<Boolean>
        get() = _isValid

    private val _navigateBack = MutableLiveData<Boolean>()

    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    private val _showError = MutableLiveData<Boolean>()

    val showError: LiveData<Boolean>
        get() = _showError

    init {
        _isValid.postValue(false)
        _navigateBack.postValue(false)
        loadCategories()
        loadTemperatures()
    }

    private fun loadCategories() {
        if (_categoriesLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("categories").get()
                .addOnSuccessListener { result ->
                    val list = ArrayList<CategoryResponseItem>()
                    for (document in result) {
                        list.add(
                                CategoryResponseItem(
                                        document.id,
                                        document.get("name") as String
                                )
                        )
                    }
                    _categories.postValue(list)
                    _categoriesLoadingStatus.value = ApiStatus.DONE
                }.addOnFailureListener { result ->
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

    private fun loadTemperatures() {
        if (_temperaturesLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("temperature").get()
                .addOnSuccessListener { result ->
                    val list = ArrayList<TemperatureResponseItem>()
                    for (document in result) {
                        list.add(
                            TemperatureResponseItem(
                                document.id,
                                document.get("name") as String,
                                document.getLong("from") as Long,
                                document.getLong("to") as Long
                            )
                        )
                    }
                    _temperatures.postValue(list)
                    _temperaturesLoadingStatus.value = ApiStatus.DONE
                }.addOnFailureListener { result ->
                    _temperatures.postValue(ArrayList())
                    _temperaturesLoadingStatus.value = ApiStatus.ERROR
                }

        viewModelScope.launch {
            try {

                _temperaturesLoadingStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _temperatures.value = null
                _temperaturesLoadingStatus.value = ApiStatus.ERROR
            }
        }
    }

    fun selectCategory(position: Int) {
        _selectedCategory.postValue(_categories.value?.get(position))
        _isValid.postValue(_pathToImage.value != null)
    }

    fun selectTemperature(position: Int) {
        _selectedTemperature.postValue(_temperatures.value?.get(position))
        _isValid.postValue(_pathToImage.value != null)
    }

    fun setPathToImage(path: Uri) {
        _pathToImage.postValue(path)
        _isValid.postValue(true)
        _isValid.postValue(_selectedCategory.value != null)
    }

    fun uploadImage() {
        val imageId = UUID.randomUUID().toString()
        FirebaseStorage.getInstance().reference.child(imageId)
            .putFile(_pathToImage.value!!)
            .addOnFailureListener {
                _showError.postValue(true)
            }
        val cloth = hashMapOf(
            "category_id" to _selectedCategory.value!!.id,
            "image_url" to imageId,
            "temperature_url" to _selectedTemperature.value!!.id
        )
        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("clothes").add(cloth)
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