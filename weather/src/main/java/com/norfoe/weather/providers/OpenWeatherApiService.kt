package com.norfoe.weather.providers

import com.norfoe.weather.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("weather")
    fun GetWeather(@Query("q") q: String, @Query("appid") appid: String) : Call<WeatherInfo>
}