package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.AboutModel

interface AboutUseCasesInterface {

    suspend fun getAbout(): Resource<List<AboutModel>>

    suspend fun getAboutById(id: Int): Resource<AboutModel>
}