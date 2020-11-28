package com.norfoe.weather

data class WeatherInfo(
    val temperature: Float? = 0.0f,
    val temperature_feels: Float? = 0.0f,
    val windSpeed: Float? = 0.0f,
    val windDirection: Int? = 0,
    val pressure: Int? = 0,
    val humidity: Int? = 0,
    val location: String? = "")