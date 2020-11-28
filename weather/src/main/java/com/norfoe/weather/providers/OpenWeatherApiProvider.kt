package com.norfoe.weather.providers

import com.norfoe.weather.Provider
import com.norfoe.weather.WeatherInfo
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse

class OpenWeatherApiProvider : Provider {
    private lateinit var config: HashMap<String, String>
    private var initialized = false

    private lateinit var retrofit: Retrofit

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

    override suspend fun fetch(cityName: String): WeatherInfo {
        val weather = OpenWeatherApi.retrofitService.getWeather(cityName, config["appid"])
        return WeatherInfo(weather.main?.temp)
    }

    override suspend fun fetch(lat: String, lon: String): WeatherInfo {
        TODO("Not yet implemented")
    }
}