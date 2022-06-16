package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toFeedback
import com.softserve.teachua.data.dto.FeedbackResponseDto
import com.softserve.teachua.data.dto.FeedbacksDto
import com.softserve.teachua.data.model.FeedbackModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.FeedbacksUseCasesInterface
import javax.inject.Inject

class FeedbacksUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) :
    FeedbacksUseCasesInterface {
    override suspend fun getFeedbacksById(clubId: Int): Resource<List<FeedbackModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getFeedbacksByClubId(clubId) },
            map = {it.toFeedback()}
        )
    }

    override suspend fun postFeedback(
        token: String,
        feedbacksDto: FeedbacksDto,
    ): Resource<FeedbacksDto> {
        return performGetFromRemote(networkCall = {
            remoteDataSource.postFeedback(token,
                feedbacksDto)
        })
    }

}