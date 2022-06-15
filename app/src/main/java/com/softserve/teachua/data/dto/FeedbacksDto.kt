package com.softserve.teachua.data.dto

data class FeedbacksDto(
   var id:Int,
   var rate:Float,
   var text:String,
   var userId: Int,
   var clubId: Int,
) {
}
