package com.softserve.teachua.app.tools

import com.softserve.teachua.data.dto.*
import com.softserve.teachua.data.model.*

internal fun BannersDto.toBanner(): BannerModel {
    return BannerModel(
        bannerId = id,
        bannerTitle = title,
        bannerSubtitle = subtitle,
        bannerLink = link,
        bannerPicture = picture,
        bannerSequenceNumber = sequenceNumber
    )

}

internal fun List<BannersDto>.toBanner(): List<BannerModel> {
    return map { it.toBanner() }
}


internal fun NewsDto.toNews(): NewsModel {
    return NewsModel(
        newsId = id,
        newsTitle = title,
        newsDescription = description,
        newsUrlTitleLogo = urlTitleLogo
    )

}

internal fun List<NewsDto>.toNewsModelMap(): List<NewsModel> {
    return map { it.toNews() }
}

internal fun CategoryDto.toCategory(): CategoryModel {
    return CategoryModel(
        categoryId = id,
        categorySortby = sortBy,
        categoryName = name,
        categoryDescription = description,
        categoryUrlLogo = urlLogo,
        categoryBackgroundColor = backgroundColor,
        categoryTagBackgroundColor = tagBackgroundColor,
        categoryTagTextColor = tagTextColor
    )

}

internal fun List<CategoryDto>.toCategory(): List<CategoryModel> {
    return map { it.toCategory() }
}

internal fun ClubDescriptionDto.toClub(): ClubModel {
    return ClubModel(
        clubId = id,
        clubName = name,
        clubDescription = description,
        clubImage = categories[0].urlLogo,
        clubBackgroundColor = categories[0].backgroundColor,
        clubCategoryName = categories[0].name,
        clubRating = rating,
        clubBanner = urlBackground

    )

}

internal fun CitiesDto.toCity(): CityModel {
    return CityModel(
        cityId = id,
        cityName = name,
        cityLatitude = latitude,
        cityLongtitude = longtitude
    )
}

internal fun List<CitiesDto>.toCity(): List<CityModel> {
    return map { it.toCity() }
}

internal fun DistrictsDto.toDistrict(): DistrictModel {
    return DistrictModel(
        districtId = id,
        districtName = name,
        cityName = cityName
    )
}

internal fun List<DistrictsDto>.toDistrict(): List<DistrictModel> {
    return map { it.toDistrict() }
}

internal fun StationsDto.toStation(): StationModel {
    return StationModel(
        stationId = id,
        stationName = name,
        cityName = cityName,
        districtName = districtName
    )
}

internal fun List<StationsDto>.toStation(): List<StationModel> {
    return map { it.toStation() }
}


internal fun ChallengeDto.toChallenge(): ChallengeModel {
    return ChallengeModel(
        id = id,
        isActive = isActive,
        name = name,
        sortNumber = sortNumber?:-1,
        title = title?:"",
        tasks = tasks?:emptyList(),
        picture = picture?:"",
        description = description?:"",
        registrationLink = registrationLink?:"",
        user = user
    )
}

internal fun List<ChallengeDto>.toChallengeModelMap(): List<ChallengeModel> {
    return map { it.toChallenge() }
}