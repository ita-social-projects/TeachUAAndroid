package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toMessage
import com.softserve.teachua.data.dto.MessageDto
import com.softserve.teachua.data.dto.MessageResponseDto
import com.softserve.teachua.data.model.MessageModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.MessagesUseCasesInterface
import javax.inject.Inject

class MessageUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : MessagesUseCasesInterface {
    override suspend fun getMessagesByRecipientId(
        token: String,
        recipientId: Int,
    ): Resource<List<MessageModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getMessagesByRecipientId(token, recipientId) },
            map = { it.toMessage() }
        )
    }

    override suspend fun sendMessage(
        token: String,
        messageDto: MessageDto,
    ): Resource<MessageResponseDto> {
        return performGetFromRemote(
            networkCall = { remoteDataSource.sendMessage(token, messageDto) }
        )

    }
}