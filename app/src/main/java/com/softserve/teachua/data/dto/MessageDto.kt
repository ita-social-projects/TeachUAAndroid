package com.softserve.teachua.data.dto

data class MessageDto(
    var id: Int,
    var clubId: Int,
    var text: String,
    var senderId: Int,
    var recipientId: Int,
    var isActive: Boolean = true,
) {
}