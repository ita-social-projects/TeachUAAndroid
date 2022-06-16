package com.softserve.teachua.data.dto

data class FeedbackResponseDto(
    var id: Int,
    var rate: Float,
    var text: String?,
    var date: List<Int>,
    var user: FeedbackUserDto
) {
}