package com.softserve.teachua.data.model

data class ClubModel(
    var clubId: Int,
    var clubName: String,
    var clubDescription: String,
    var clubImage: String,
    var clubBackgroundColor: String,
    var clubCategoryName: String,
    var clubRating: Float,
    var clubBanner: String?
) {
}