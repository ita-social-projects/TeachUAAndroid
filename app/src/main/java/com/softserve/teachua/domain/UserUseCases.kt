package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.app.tools.performGetFromRemote
import com.softserve.teachua.data.dto.UpdateUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.data.retrofit.datasource.RemoteDataSource
import com.softserve.teachua.domain.interfaces.UserUseCasesInterface
import javax.inject.Inject

class UserUseCases @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : UserUseCasesInterface
{
    override suspend fun getUserById(token: String, id: Int): Resource<UserDto> {
        return performGetFromRemote(
            networkCall = { remoteDataSource.getUserById(token, id) },
            )
    }

    override suspend fun updateUserById(
        token: String,
        id: Int,
        updateUserDto: UpdateUserDto,
    ): Resource<UpdateUserDto> {
        return performGetFromRemote { remoteDataSource.updateUserById(token, id, updateUserDto) }
    }
}