package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.data.dto.TaskDto
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.TaskUseCasesInterface
import javax.inject.Inject

class TaskUseCases @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    TaskUseCasesInterface {
    override suspend fun getTask(id: Int): Resource<TaskDto> {
        return performGetFromRemote(
            networkCall = { remoteDataSource.getTask(id) }
        )
    }
}