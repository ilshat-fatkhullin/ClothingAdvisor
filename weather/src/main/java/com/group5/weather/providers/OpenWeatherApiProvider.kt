package com.group5.weather.providers

import com.group5.weather.Provider
import com.group5.weather.WeatherInfo
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse

class OpenWeatherApiProvider : Provider {
    private lateinit var config: HashMap<String, String>
    private var initialized = false

    private lateinit var retrofit: Retrofit

    constructor() {
        config = HashMap<String, String>()
        config["appid"] = "f6f81ef69442b7b7f2ba4bf66326a285"
        init()
    }

    constructor(config: HashMap<String, String>) {
        this.config = config
        init()
    }

    override fun init() {
        initialized = config.containsKey("appid")
    }

    override fun setConfig(config: HashMap<String, String>) {
        this.config = config
    }

    override suspend fun fetch(cityName: String?): WeatherInfo {
        val weather = OpenWeatherApi.retrofitService.getWeather(cityName, config["appid"])
        return WeatherInfo(
            weather.main?.temp,
            weather.main?.feels_like,
            weather.wind?.speed?.toFloat(),
            weather.wind?.deg,
            weather.main?.pressure,
            weather.main?.humidity,
            "${weather.name}, ${weather.sys?.country}")
    }

    override suspend fun fetch(lat: String?, lon: String?): WeatherInfo {
        TODO("Not yet implemented")
    }
}