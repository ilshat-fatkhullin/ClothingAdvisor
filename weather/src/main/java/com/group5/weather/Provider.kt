package com.group5.weather

interface Provider {
    fun init();

    /**
     * Config is used to pass various data to the provider, e.g. an API key
     */
    fun setConfig(config: HashMap<String, String>)

    suspend fun fetch(cityName: String? = ""): WeatherInfo
    suspend fun fetch(lat: String? = "0", lon: String? = "0"): WeatherInfo
}