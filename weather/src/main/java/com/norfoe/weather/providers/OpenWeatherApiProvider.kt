package com.norfoe.weather.providers

import com.norfoe.weather.Provider
import com.norfoe.weather.WeatherInfo

class OpenWeatherApiProvider : Provider {
    private lateinit var config: HashMap<String, String>
    private var initialized = false

    override fun Provider() {
        TODO("Not yet implemented")
    }

    override fun Provider(config: HashMap<String, String>) {
        this.config = config
        init()
    }

    override fun init() {
        initialized = config.containsKey("apikey")
    }

    override fun setConfig(config: HashMap<String, String>) {
        this.config = config
    }

    override fun fetch(cityName: String): WeatherInfo {
        TODO("Not yet implemented")
    }

    override fun fetch(lat: String, lon: String): WeatherInfo {
        TODO("Not yet implemented")
    }
}