package com.softserve.teachua.data.dto


data class TaskDto(
    val headerText: String,
    val id: Int,
    val name: String,
    val picture: String,
    val startDate: List<Int>,
    val description : String
)