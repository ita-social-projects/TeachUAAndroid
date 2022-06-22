package com.softserve.teachua.domain

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.CurrentUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.data.dto.UserLoginDto
import com.softserve.teachua.data.sharedpreferences.CurrentUserSharedPreferencesInterface
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface

class CurrentUserUseCases(
    private val userSource: CurrentUserSharedPreferencesInterface,
) : CurrentUserUseCasesInterface
{
    override fun clearCurrentUser() {
        userSource.clearCurrentUser()
    }

    override fun getCurrentUser(): Resource<CurrentUserDto> {
        return userSource.getCurrentUser()
    }

    override fun setCurrentUser(currentUserDto: CurrentUserDto) {
        userSource.setCurrentUser(currentUserDto)
    }

    override fun clearUser() {
        userSource.clearUser()
    }

    override fun getUser(): Resource<UserDto> {
        return userSource.getUser()
    }

    override fun setUser(userDto: UserDto) {
        userSource.setUser(userDto)
    }

    override fun clearUserCredentials() {
        userSource.clearUserCredentials()
    }

    override fun getUserCredentials(): Resource<UserLoginDto> {
        return userSource.getUserCredentials()
    }

    override fun setUserCredentials(userLoginDto: UserLoginDto) {
        userSource.setUserCredentials(userLoginDto)
    }

    override fun getCurrentApiVersion(): Int{
        return userSource.getCurrentApiVersion()
    }

    override fun setCurrentApiVersion(version: Int){
        userSource.setCurrentApiVersion(version)
    }


}