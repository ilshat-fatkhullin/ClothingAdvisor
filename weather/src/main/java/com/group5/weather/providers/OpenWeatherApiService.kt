package com.group5.weather.providers

import com.group5.weather.WeatherInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherApiService {
    @GET("weather")
    suspend fun getWeather(@Query("q") q: String? = "", @Query("appid") appid: String? = "")
            : OpenWeatherApiWeatherResponseItem
}

object OpenWeatherApi {
    val retrofitService : OpenWeatherApiService by lazy { retrofit.create(OpenWeatherApiService::class.java) }
}