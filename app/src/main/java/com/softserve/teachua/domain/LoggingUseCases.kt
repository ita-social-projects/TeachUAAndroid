package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.data.dto.UserLoggedDto
import com.softserve.teachua.data.dto.UserLoginDto
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.LoggingUseCasesInterface
import javax.inject.Inject

class LoggingUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : LoggingUseCasesInterface
{
    override suspend fun getLoggedUser(userLoginDto: UserLoginDto): Resource<UserLoggedDto> {
        return performGetFromRemote(
            networkCall = { remoteDataSource.getLoggedUser(userLoginDto) },
            )

    }
}