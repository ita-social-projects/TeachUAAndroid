package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toAbout
import com.softserve.teachua.app.tools.toAboutModelMap
import com.softserve.teachua.data.model.AboutModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.AboutUseCasesInterface
import javax.inject.Inject

class AboutUseCases @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    AboutUseCasesInterface {
    override suspend fun getAbout(): Resource<List<AboutModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAbout() },
            map = { it.toAboutModelMap() }
        )
    }

    override suspend fun getAboutById(id: Int): Resource<AboutModel> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAboutById(id) },
            map = { it.toAbout() }
        )
    }
}