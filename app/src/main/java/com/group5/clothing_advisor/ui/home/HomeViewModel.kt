package com.group5.clothing_advisor.ui.home

import android.util.Log
import android.util.Log.INFO
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.ClothResponseItem
import com.group5.clothing_advisor.data.TemperatureResponseItem
import com.group5.weather.Weather
import com.group5.weather.WeatherInfo
import com.group5.weather.providers.OpenWeatherApiProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<CategoryResponseItem>>(ArrayList())

    private val _categoriesLoadingStatus = MutableLiveData<ApiStatus>()

    val recommendation: LiveData<List<ClothResponseItem>>
        get() = _recommendation

    private val _recommendation = MutableLiveData<List<ClothResponseItem>>()

    val recommendationLoadingStatus: LiveData<ApiStatus>
        get() = _recommendationLoadingStatus

    private val _recommendationLoadingStatus = MutableLiveData<ApiStatus>()

    private val _provider = OpenWeatherApiProvider()

    private val _weather = Weather(_provider)

    private val _weatherInfo = MutableLiveData<WeatherInfo>()

    private val _temperatureOption = MutableLiveData<TemperatureResponseItem>()

    private val _optionLoadingStatus = MutableLiveData<ApiStatus>()

    init {
        _recommendation.value = ArrayList()
        loadCategories()
        getTemperatureOption()
        loadRecommendation()
    }

    fun loadRecommendation() {
        if (_recommendationLoadingStatus.value == ApiStatus.LOADING)
            return

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("clothes").get()
            .addOnSuccessListener { result ->
                val list = ArrayList<ClothResponseItem>()
                for (category in _categories.value!!) {
                    for (document in result) {
                        val categoryId = document.getString("category_id")
                        if (_temperatureOption.value != null &&
                            _temperatureOption.value!!.id == document.getString("temperature_id")
                            && category.id == categoryId
                        ) {

                            list.add(
                                ClothResponseItem(
                                    document.id,
                                    document.getString("image_url")!!,
                                    getCategoryNameById(categoryId),
                                    _temperatureOption.value!!.name
                                )
                            )
                            break
                        }

                    }
                }
                _recommendation.postValue(list)
                _recommendationLoadingStatus.value = ApiStatus.DONE
            }
            .addOnFailureListener {
                _recommendationLoadingStatus.value = ApiStatus.ERROR
            }
    }

    private fun getTemperatureOption() {
        getWeather()
        FirebaseFirestore.getInstance().collection("temperature").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (_weatherInfo.value?.temperature!! >= document.getLong("from")!!
                        && _weatherInfo.value?.temperature!! < document.getLong("to")!!
                    ) {
                        _temperatureOption.value = TemperatureResponseItem(
                            document.id,
                            document.get("name") as String,
                            document.getLong("from") as Long,
                            document.getLong("to") as Long
                        )
                        break
                    }
                }
                _optionLoadingStatus.value = ApiStatus.DONE
            }.addOnFailureListener { result ->
                _optionLoadingStatus.value = ApiStatus.ERROR
            }
    }

    private fun getWeather() {
        _weather.setCity("Innopolis,RU")
        runBlocking {
            _weatherInfo.value = _weather.current()
        }
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
    }

    private fun getCategoryNameById(id: String): String {
        for (category in _categories.value!!) {
            if (category.id == id)
                return category.name
        }
        return ""
    }
}