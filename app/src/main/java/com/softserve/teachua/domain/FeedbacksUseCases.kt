package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.data.dto.FeedbacksDto
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.FeedbacksUseCasesInterface
import javax.inject.Inject

class FeedbacksUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) :
    FeedbacksUseCasesInterface {
    override suspend fun getFeedbacksById(clubId: Int): Resource<ArrayList<FeedbacksDto>> {
        return performGetFromRemote(
            networkCall = { remoteDataSource.getFeedbacksById(clubId) }
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