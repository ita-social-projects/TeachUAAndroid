package com.softserve.teachua.data.model

import com.softserve.teachua.data.dto.ContactsDto

data class ClubModel(
    var clubId: Int,
    var clubName: String,
    var clubDescription: String,
    var clubImage: String,
    var clubBackgroundColor: String,
    var clubCategoryName: String,
    var clubRating: Float,
    var clubContacts: List<ContactsDto>,
    var clubBanner: String?
) {
}