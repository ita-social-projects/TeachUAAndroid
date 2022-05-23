package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.DistrictModel

interface DistrictUseCasesInterface {
    suspend fun getAllDistricts(cityName: String): Resource<List<DistrictModel>>
}
