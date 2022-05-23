package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.BannerModel

interface BannerUseCasesInterface {
    suspend fun getAllBanners(): Resource<List<BannerModel>>
}
