package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.app.tools.toNews
import com.softserve.teachua.app.tools.toNewsModelMap
import com.softserve.teachua.data.model.NewsModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.NewsUseCasesInterface
import javax.inject.Inject

class NewsUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : NewsUseCasesInterface {
    override suspend fun getNews(): Resource<List<NewsModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getNews() },
            map = { it.toNewsModelMap() }
        )
    }

    override suspend fun getNewsById(id: Int): Resource<NewsModel> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getNewsById(id) },
            map = { it.toNews() }
        )
    }

}