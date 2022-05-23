package com.softserve.teachua.data.model

data class BannerModel(
    var bannerId: Int,
    var bannerTitle: String,
    var bannerSubtitle: String?,
    var bannerLink: String?,
    var bannerPicture: String?,
    var bannerSequenceNumber: Int?
)
