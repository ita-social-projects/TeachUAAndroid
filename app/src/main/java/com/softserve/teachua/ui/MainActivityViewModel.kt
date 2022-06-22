package com.softserve.teachua.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val currentUserUseCases: CurrentUserUseCasesInterface,
) : ViewModel()
{

    private var _user: MutableStateFlow<Resource<UserDto>> =
        MutableStateFlow(Resource.loading())

    private var _version: MutableStateFlow<Int> = MutableStateFlow(currentUserUseCases.getCurrentApiVersion())

    val version: StateFlow<Int>
        get() = _version

    val user: StateFlow<Resource<UserDto>>
        get() = _user


    //Delay is made
    fun loadUser() =
        viewModelScope.launch {
            delay(100)
            _user.value = Resource.loading()
            _user.value = currentUserUseCases.getUser()

        }


    fun logOut() {
        viewModelScope.launch {
            _user.value = Resource.error()
            currentUserUseCases.clearCurrentUser()
            currentUserUseCases.clearUser()
        }
    }

    fun loadApiVersion(){
        viewModelScope.launch {
            delay(100)
            _version.value = currentUserUseCases.getCurrentApiVersion()
        }
    }

    fun setApiVersion(version: Int){
        viewModelScope.launch {
            currentUserUseCases.setCurrentApiVersion(version)
        }
    }

}