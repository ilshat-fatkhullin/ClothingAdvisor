package com.norfoe.weather

interface Provider {
    fun Provider()
    fun Provider(config: HashMap<String, String>)

    /**
     * Config is used to pass various data to the provider, e.g. an API key
     */
    fun setConfig(config: HashMap<String, String>)

    fun fetch(cityName: String): WeatherInfo
    fun fetch(lat: String, lon: String): WeatherInfo
}