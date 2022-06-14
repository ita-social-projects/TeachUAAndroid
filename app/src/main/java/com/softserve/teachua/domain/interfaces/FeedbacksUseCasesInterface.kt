package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.FeedbacksDto

interface FeedbacksUseCasesInterface {
    suspend fun getFeedbacksById(clubId: Int): Resource<ArrayList<FeedbacksDto>>
}