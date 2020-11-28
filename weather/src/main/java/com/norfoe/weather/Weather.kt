package com.norfoe.weather

class Weather {
    private lateinit var provider: Provider

    private var cityName: String? = ""

    constructor(provider: Provider, config: HashMap<String, String>) {
        this.provider = provider
        this.provider.setConfig(config)
    }

    constructor(provider: Provider) {
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