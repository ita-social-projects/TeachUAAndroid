package com.softserve.teachua.ui.register

import androidx.lifecycle.ViewModel
import com.softserve.teachua.data.dto.CurrentUserDto
import com.softserve.teachua.data.dto.UserDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {


    /*
    UserDto
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val password: String,
    val roleName: String,
    val verificationCode: String
    val urlLogo: String,
    val status: Boolean,
     */
    fun signUp(
        userDto: UserDto
    ){

    }

}