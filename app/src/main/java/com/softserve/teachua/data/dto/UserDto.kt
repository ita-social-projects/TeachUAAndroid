package com.softserve.teachua.data.dto

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val password: String,
    val roleName: String,
    val urlLogo: String,
    val status: Boolean,
    val logInTime: Long
)