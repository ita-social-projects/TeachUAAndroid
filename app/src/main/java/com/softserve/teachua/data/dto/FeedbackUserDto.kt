package com.softserve.teachua.data.dto

data class FeedbackUserDto(
    var id: Int,
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var urlLogo: String,
) {
}