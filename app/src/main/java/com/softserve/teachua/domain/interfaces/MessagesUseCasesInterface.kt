package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.MessageDto
import com.softserve.teachua.data.dto.MessageResponseDto
import com.softserve.teachua.data.model.MessageModel

interface MessagesUseCasesInterface {
    suspend fun getMessagesByRecipientId(
        token: String,
        recipientId: Int,
    ): Resource<List<MessageModel>>

    suspend fun sendMessage(token: String, messageDto: MessageDto): Resource<MessageResponseDto>
}