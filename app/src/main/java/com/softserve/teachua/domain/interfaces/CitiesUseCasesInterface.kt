package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.CityModel

interface CitiesUseCasesInterface {
    suspend fun getAllCities(): Resource<List<CityModel>>
}
