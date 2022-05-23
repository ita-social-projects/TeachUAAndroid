package com.softserve.teachua.data.dto

data class CategoryDto(
    var id: Int,
    var sortBy: Int,
    var name: String,
    var description: String,
    var urlLogo: String,
    var backgroundColor: String,
    var tagBackgroundColor: String,
    var tagTextColor: String
)
