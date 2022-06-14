package com.softserve.teachua.data.dto

 data class MessageResponseDto(
    var id: Int,
    var text: String,
    var date: Array<Int>?,
    var club: MessageClubDto,
    var sender: MessageUserDto,
    var recipient: MessageUserDto,
    var isActive: Boolean
) {
    override fun equals(other: Any?): Boolean {
       if (this === other) return true
       if (javaClass != other?.javaClass) return false

       other as MessageResponseDto

       if (id != other.id) return false
       if (text != other.text) return false
       if (date != null) {
          if (other.date == null) return false
          if (!date.contentEquals(other.date)) return false
       } else if (other.date != null) return false
       if (club != other.club) return false
       if (sender != other.sender) return false
       if (recipient != other.recipient) return false
       if (isActive != other.isActive) return false

       return true
    }

    override fun hashCode(): Int {
       var result = id
       result = 31 * result + text.hashCode()
       result = 31 * result + (date?.contentHashCode() ?: 0)
       result = 31 * result + club.hashCode()
       result = 31 * result + sender.hashCode()
       result = 31 * result + recipient.hashCode()
       result = 31 * result + isActive.hashCode()
       return result
    }

 }