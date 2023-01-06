package com.fortyone.weathertest.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fortyone.weathertest.ui.main.api.RetrofitInstance
import com.fortyone.weathertest.ui.main.datamodel.CurrentWeather
import com.fortyone.weathertest.ui.main.datamodel.Location
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel : ViewModel() {

    var location: Location? = null

    private val _locationList = MutableLiveData<List<Location>>()
    val locationList : LiveData<List<Location>> = _locationList

    private val _weather = MutableLiveData<CurrentWeather>()
    val weather :LiveData<CurrentWeather> = _weather

    private var _imgUrl = MutableLiveData<String>()
    val imgUrl: LiveData<String> = _imgUrl

    private var _currentQuery = MutableLiveData<String>()

    init {
        _weather.postValue(CurrentWeather())
        location = null
    }

    //Coroutine to get locations from OpenWeatherMap using Retrofit for REST API
    fun onSearch() {
        viewModelScope.launch {
           val response = try {
               RetrofitInstance.api.getLocation(_currentQuery.value!!)
           } catch (e: IOException) {
               Log.e("ViewModel", "You may not have an internet connection")
               return@launch
           } catch (e: HttpException) {
               Log.e("ViewModel", "Unexpected Response")
               return@launch
           }
            if (response.isSuccessful && response.body() != null) {
                _locationList.postValue(response.body()!!)
            }
        }
    }

    //Coroutine to get weather data from OpenWeatherMap using Retrofit for REST API
    private fun getCurrentWeather() {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.getWeather(location?.lat.toString(),location?.lon.toString())
            } catch (e: IOException) {
                Log.e("ViewModel", "You may not have an internet connection")
                return@launch
            } catch (e: HttpException) {
                Log.e("ViewModel", "Unexpected Response")
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                _weather.postValue(response.body()!!)
                _imgUrl.postValue("http://openweathermap.org/img/wn/${response.body()!!.current.weather[0].icon}@2x.png")
            }
        }
    }

    //Called by the UI, to let the ViewModel know the current state of the search field
    fun onQueryTextChanged(s: CharSequence,start: Int,before : Int,
                           count :Int){
        _currentQuery.postValue(s.toString())
    }

    //Store the selected location upon search completion
    fun updateLocation(newLocation: Location) {
        location = newLocation
        getCurrentWeather()
    }
}