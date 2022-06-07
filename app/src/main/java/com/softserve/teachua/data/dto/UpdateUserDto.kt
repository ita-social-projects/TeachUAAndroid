package com.softserve.teachua.data.dto

data class UpdateUserDto(
    var id: Int,
    var email: String,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var urlLogo: String,
    var status: Boolean,
    var roleName: String,
) {
}