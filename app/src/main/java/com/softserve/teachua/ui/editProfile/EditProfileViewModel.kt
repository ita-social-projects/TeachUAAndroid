package com.softserve.teachua.ui.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.UpdateUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.domain.UserUseCases
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface
import com.softserve.teachua.domain.interfaces.UserUseCasesInterface
import com.softserve.teachua.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val currentUserUseCases: CurrentUserUseCasesInterface,
    private val userUseCases: UserUseCasesInterface,
) :
    ViewModel() {

    private var _currentUserProfile = MutableStateFlow<Resource<UserDto>>(Resource.loading())
    private var _updatedUserProfile = MutableStateFlow<Resource<UpdateUserDto>>(Resource.loading())
    private var _userProfile = MutableStateFlow<Resource<UserDto>>(Resource.loading())

    val currentUserProfile: StateFlow<Resource<UserDto>>
        get() = _currentUserProfile

    val updatedUserProfile: StateFlow<Resource<UpdateUserDto>>
        get() = _updatedUserProfile

    val userProfile: StateFlow<Resource<UserDto>>
        get() = _userProfile



    fun loadUserFromLocal() =
        viewModelScope.launch {
            _currentUserProfile.value = Resource.loading()
            _currentUserProfile.value = currentUserUseCases.getUser()
            println("token" + currentUserUseCases.getCurrentUser().data?.token)

        }


    fun updateUser(updateUserDto: UpdateUserDto) {

        viewModelScope.launch {
            _updatedUserProfile.value = Resource.loading()
            _updatedUserProfile.value =
                userUseCases.updateUserById(currentUserUseCases.getCurrentUser().data?.token!!,
                    currentUserUseCases.getCurrentUser().data?.id!!,
                    updateUserDto)



        }
    }

    fun updateUserInLocal() {

        viewModelScope.launch {
           // currentUserUseCases.clearUser()
            var userDto = UserDto(
                id = updatedUserProfile.value.data?.id!!,
                firstName = updatedUserProfile.value.data?.firstName!!,
                lastName = updatedUserProfile.value.data?.lastName!!,
                phone = updatedUserProfile.value.data?.phone!!,
                email = updatedUserProfile.value.data?.email!!,
                password = currentUserProfile.value.data?.password!!,
                roleName = updatedUserProfile.value.data?.roleName!!,
                urlLogo = updatedUserProfile.value.data?.urlLogo!!,
                status = updatedUserProfile.value.data?.status!!,
                logInTime = System.currentTimeMillis()
            )
            currentUserUseCases.setUser(userDto)
            delay(1000)




        }
    }


}