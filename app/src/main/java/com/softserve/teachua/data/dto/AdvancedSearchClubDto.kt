package com.softserve.teachua.data.dto

data class AdvancedSearchClubDto(
    var name: String,
    var age: Int?,
    var districtName: String?,
    var cityName: String,
    var stationName: String?,
    var sort: String,
    var page: Int,
    var categoriesName: List<String>,
    var isOnline: Boolean,
    var isCenter: Boolean,
    var isAdvanced: Boolean,
) {


}