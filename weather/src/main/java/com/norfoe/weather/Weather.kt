package com.norfoe.weather

class Weather {
    private lateinit var provider: Provider

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

    fun setLocation(city: String) {

    }
}