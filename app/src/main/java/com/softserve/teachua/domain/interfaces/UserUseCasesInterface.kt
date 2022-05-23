package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.UserDto

interface UserUseCasesInterface {
    suspend fun getUserById(token: String, id: Int): Resource<UserDto>

}
