package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.FeedbackResponseDto
import com.softserve.teachua.data.dto.FeedbacksDto
import com.softserve.teachua.data.model.FeedbackModel

interface FeedbacksUseCasesInterface {
    suspend fun getFeedbacksById(clubId: Int): Resource<List<FeedbackModel>>
    suspend fun postFeedback(token: String, feedbacksDto: FeedbacksDto): Resource<FeedbacksDto>
}