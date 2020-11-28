package com.norfoe.weather.providers

import com.norfoe.weather.Provider
import com.norfoe.weather.WeatherInfo

class OpenWeatherApiProvider : Provider {
    private lateinit var config: HashMap<String, String>
    private var initialized = false

    override fun Provider() {
        val config = HashMap<String, String>()
        config["appid"] = "1734842d30f08a342f6c4da064960181"
        Provider(config)
    }

    override fun Provider(config: HashMap<String, String>) {
        this.config = config
        init()
    }

    override fun init() {
        initialized = config.containsKey("appid")
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