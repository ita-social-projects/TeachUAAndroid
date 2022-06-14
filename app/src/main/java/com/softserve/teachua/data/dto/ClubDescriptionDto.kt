package com.softserve.teachua.data.dto

data class ClubDescriptionDto(
    var id: Int,
    var name: String,
    var description: String,
    var urlLogo: String,
    var urlBackground: String?,
    var categories: List<CategoryDto>,
    var contacts: List<ContactsDto>,
    var rating: Float
) {


}