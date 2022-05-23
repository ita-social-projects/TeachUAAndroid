package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toDistrict
import com.softserve.teachua.data.model.DistrictModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.DistrictUseCasesInterface
import javax.inject.Inject

class DistrictUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DistrictUseCasesInterface
{

    override suspend fun getAllDistricts(cityName: String): Resource<List<DistrictModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAllDistricts(cityName) },
            map = { it.toDistrict() }
        )

    }
}