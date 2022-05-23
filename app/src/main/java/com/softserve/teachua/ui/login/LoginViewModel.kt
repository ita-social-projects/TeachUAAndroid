package com.softserve.teachua.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.*
import com.softserve.teachua.domain.LoggingUseCases
import com.softserve.teachua.domain.UserUseCases
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loggingUseCases: LoggingUseCases,
    private val userUseCases: UserUseCases,
    private val currentUserUseCases: CurrentUserUseCasesInterface,
) : ViewModel() {


    private var _user: MutableStateFlow<Resource<UserDto>> =
        MutableStateFlow(Resource.loading())


    val user: StateFlow<Resource<UserDto>>
        get() = _user

    private var _loggedDto =
        MutableStateFlow<Resource<UserLoggedDto>>(Resource.loading())


    val loggedDto: StateFlow<Resource<UserLoggedDto>>
        get() = _loggedDto


    fun load(userLoginDto: UserLoginDto) = viewModelScope.launch {
        _loggedDto.value = Resource.loading()
        _loggedDto.value = loggingUseCases.getLoggedUser(userLoginDto)

        if (loggedDto.value.status == Resource.Status.SUCCESS) {
            val currentUserDto = CurrentUserDto(
                id = loggedDto.value.data?.id!!,
                email = loggedDto.value.data?.email!!,
                token = "Bearer " + loggedDto.value.data?.accessToken!!
            )
            currentUserUseCases.setCurrentUser(currentUserDto)

            val userCredentials = UserLoginDto(
                email = userLoginDto.email,
                password = userLoginDto.password
            )

            currentUserUseCases.setUserCredentials(userCredentials)


        }

    }


    fun loadUser(token: String, id: Int) = viewModelScope.launch {
        _user.value = Resource.loading()
        _user.value = userUseCases.getUserById(token, id)

        if (user.value.status == Resource.Status.SUCCESS) {
            val userDto = UserDto(
                id = user.value.data?.id!!,
                firstName = user.value.data?.firstName!!,
                lastName = user.value.data?.lastName!!,
                phone = user.value.data?.phone!!,
                email = user.value.data?.email!!,
                password = user.value.data?.password!!,
                roleName = user.value.data?.roleName!!,
                urlLogo = user.value.data?.urlLogo!!,
                status = user.value.data?.status!!,
                logInTime = System.currentTimeMillis()
            )
            currentUserUseCases.setUser(userDto)
        }
    }

}