package com.norfoe.weather

import com.norfoe.weather.providers.OpenWeatherApiProvider
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

class WeatherUnitTest {
    @Test
    fun weather() {
        val provider = OpenWeatherApiProvider()
        val weather = Weather(provider)
        weather.setCity("Innopolis,RU")
        runBlocking {
            val weatherInfo = weather.current()
            print(weatherInfo)
            assert(weatherInfo.temperature!! > 0)
        }
    }
}