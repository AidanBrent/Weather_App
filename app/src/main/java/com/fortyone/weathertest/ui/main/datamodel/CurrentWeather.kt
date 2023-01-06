package com.fortyone.weathertest.ui.main.datamodel

data class CurrentWeather(
    val dt: Int = 0,
    val current: Current = Current(),
    val daily: List<Daily> = List(7) { Daily() }
)

data class Current(
    val feels_like: Double = 0.00,
    val weather: List<Weather> = List<Weather>(1) { Weather() },
    val humidity: Int = 0,
    val pressure: Int = 0,
    val temp: Double = 0.00,
)

data class Daily(
    val dt: Int = 0,
    val weather: List<Weather> = List<Weather>(1) { Weather() },
    val humidity: Int = 0,
    val pressure: Int = 0,
    val temp: DailyTemp = DailyTemp()
)

data class Weather(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)

data class DailyTemp(
    val min: Double = 0.00,
    val max: Double = 0.00
)