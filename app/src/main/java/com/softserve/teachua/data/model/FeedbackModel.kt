package com.softserve.teachua.data.model

import com.softserve.teachua.data.dto.FeedbackUserDto

data class FeedbackModel(
    var feedBackId: Int,
    var feedBackRate: Float,
    var feedBackText: String?,
    var feedBackDate: List<Int>,
    var feedBackUser: FeedbackUserDto
) {
}