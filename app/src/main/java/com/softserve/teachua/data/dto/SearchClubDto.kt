package com.softserve.teachua.data.dto

data class SearchClubDto(
    var clubName: String,
    var cityName: String,
    var isOnline: Boolean,
    var categoryName: String,
    var page: Int,
) {
}