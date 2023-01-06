package com.fortyone.weathertest.ui.main.datamodel

data class Location(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)

data class LocalNames(
    val ascii: String,
    val en: String,
    val feature_name: String
)