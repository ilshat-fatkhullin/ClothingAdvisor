package com.norfoe.weather

class Weather {
    private lateinit var provider: Provider

    private var cityName: String? = ""

    fun Weather(provider: Provider, config: HashMap<String, String>) {
        this.provider = provider
        this.provider.setConfig(config)
    }

    fun Weather(provider: Provider) {
        this.provider = provider
    }

    fun setProvider(provider: Provider) {
        this.provider = provider
    }

    fun setCity(cityName: String?) {
        this.cityName = cityName
    }

    suspend fun current() : WeatherInfo {
        return provider.fetch(cityName)
    }
}