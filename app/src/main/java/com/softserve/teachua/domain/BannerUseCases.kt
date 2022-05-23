package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.toBanner
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.data.model.BannerModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.BannerUseCasesInterface
import javax.inject.Inject


class BannerUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : BannerUseCasesInterface
{
    override suspend fun getAllBanners(): Resource<List<BannerModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = {
                remoteDataSource.getAllBanners()
            },
            map = { it.toBanner() }
        )
    }
}