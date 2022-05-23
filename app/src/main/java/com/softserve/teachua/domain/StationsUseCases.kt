package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toStation
import com.softserve.teachua.data.model.StationModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.StationsUseCasesInterface
import javax.inject.Inject

class StationsUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : StationsUseCasesInterface
{
    override suspend fun getAllStations(cityName: String): Resource<List<StationModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAllStations(cityName) },
            map = { it.toStation() }
        )

    }
}