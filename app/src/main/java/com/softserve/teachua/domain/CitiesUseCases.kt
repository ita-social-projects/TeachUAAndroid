package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toCity
import com.softserve.teachua.data.model.CityModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.CitiesUseCasesInterface
import javax.inject.Inject

class CitiesUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : CitiesUseCasesInterface
{
    override suspend fun getAllCities(): Resource<List<CityModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAllCities() },
            map = { it.toCity() }
        )

    }
}