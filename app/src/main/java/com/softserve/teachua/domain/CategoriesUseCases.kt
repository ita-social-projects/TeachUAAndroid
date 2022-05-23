package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.toCategory
import com.softserve.teachua.app.tools.performGetFromRemoteAndMapData
import com.softserve.teachua.data.model.CategoryModel
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.CategoriesUseCasesInterface
import javax.inject.Inject

class CategoriesUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : CategoriesUseCasesInterface
{
    override suspend fun getAllCategories(): Resource<List<CategoryModel>> {
        return performGetFromRemoteAndMapData(
            networkCall = { remoteDataSource.getAllCategories() },
            map = { it.toCategory() }
        )
    }
}