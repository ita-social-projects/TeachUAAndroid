package com.softserve.teachua.data.dto

import com.google.gson.annotations.SerializedName

data class FeedbacksDto(
   var id:Int,
   var rate:Float,
   var text:String,
   var user: UserDto,
   var clubContacts: String,
) {
}
