package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.StationModel

interface StationsUseCasesInterface {
    suspend fun getAllStations(cityName: String): Resource<List<StationModel>>

}
