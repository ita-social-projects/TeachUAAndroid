package com.softserve.teachua.data.model

import com.softserve.teachua.data.dto.MessageClubDto
import com.softserve.teachua.data.dto.MessageUserDto

data class MessageModel(
    var messageId: Int,
    var messageText: String,
    var messageDate: Array<Int>?,
    var messageClub: MessageClubDto,
    var messageSender: MessageUserDto,
    var messageRecipient: MessageUserDto,
    var messageIsActive: Boolean = true,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageModel

        if (messageId != other.messageId) return false
        if (messageText != other.messageText) return false
        if (messageDate != null) {
            if (other.messageDate == null) return false
            if (!messageDate.contentEquals(other.messageDate)) return false
        } else if (other.messageDate != null) return false
        if (messageClub != other.messageClub) return false
        if (messageSender != other.messageSender) return false
        if (messageRecipient != other.messageRecipient) return false
        if (messageIsActive != other.messageIsActive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = messageId
        result = 31 * result + messageText.hashCode()
        result = 31 * result + (messageDate?.contentHashCode() ?: 0)
        result = 31 * result + messageClub.hashCode()
        result = 31 * result + messageSender.hashCode()
        result = 31 * result + messageRecipient.hashCode()
        result = 31 * result + messageIsActive.hashCode()
        return result
    }

}