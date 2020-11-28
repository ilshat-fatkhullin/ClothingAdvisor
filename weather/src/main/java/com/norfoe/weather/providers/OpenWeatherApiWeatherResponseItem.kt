package com.norfoe.weather.providers

data class OpenWeatherApiWeatherResponseItem(
    val coord: ResponseCoord? = ResponseCoord(),
    val weather: List<ResponseWeather>? = ArrayList<ResponseWeather>(),
    val base: String? = "",
    val main: ResponseMain? = ResponseMain(),
    val visibility: String? = "",
    val wind: ResponseWind? = ResponseWind(),
    val clouds: ResponseClouds? = ResponseClouds(),
    val dt: Long? = 0,
    val sys: ResponseSys? = ResponseSys(),
    val timezone: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    val cod: Int? = 0
)

data class ResponseWeather(
    val id: Int? = 0,
    val main: String? = "",
    val description: String? = "",
    val icon: String? = "")

data class ResponseCoord(
    val lon: Float? = 0.0f,
    val lat: Float? = 0.0f)

data class ResponseMain(
    val temp: Float? = 0.0f,
    val feels_like: Float? = 0.0f,
    val temp_min: Float? = 0.0f,
    val temp_max: Float? = 0.0f,
    val pressure: Int? = 0,
    val humidity: Int? = 0)

data class ResponseWind(
    val speed: Int? = 0,
    val deg: Int? = 0)

data class ResponseClouds(
    val all: Int? = 0)

data class ResponseSys(
    val type: Int? = 0,
    val id: Int? = 0,
    val country: String? = "",
    val sunrise: Long? = 0,
    val sunset: Long? = 0)
