package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.UserLoggedDto
import com.softserve.teachua.data.dto.UserLoginDto

interface LoggingUseCasesInterface {
    suspend fun getLoggedUser(userLoginDto: UserLoginDto): Resource<UserLoggedDto>

}
