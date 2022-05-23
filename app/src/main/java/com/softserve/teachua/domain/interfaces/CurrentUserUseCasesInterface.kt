package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.CurrentUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.data.dto.UserLoginDto

interface CurrentUserUseCasesInterface {
    fun clearCurrentUser()

    fun getCurrentUser(): Resource<CurrentUserDto>

    fun setCurrentUser(currentUserDto: CurrentUserDto)

    fun clearUser()

    fun getUser(): Resource<UserDto>

    fun setUser(userDto: UserDto)

    fun clearUserCredentials()

    fun getUserCredentials(): Resource<UserLoginDto>

    fun setUserCredentials(userLoginDto: UserLoginDto)

}
