package com.fortyone.weathertest.ui.main.api

import android.util.Log
import com.fortyone.weathertest.BuildConfig
import com.fortyone.weathertest.ui.main.datamodel.CurrentWeather
import com.fortyone.weathertest.ui.main.datamodel.Location
import com.fortyone.weathertest.ui.main.datamodel.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Hidden API key obtained from OpenWeatherMap
const val AppID = BuildConfig.API_KEY

interface WeatherApi {

    @GET("/geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") searchString : String,
        @Query("limit") resultLimit : String = "0",
        @Query("appid") appID: String = AppID
    ): Response<List<Location>>

    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("exclude") exclude: String = "minutely",
        @Query("units") tempUnit: String = "metric",
        @Query("appid") appID: String = AppID
    ): Response<CurrentWeather>
}