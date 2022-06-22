package com.softserve.teachua.data.sharedpreferences

import android.content.Context
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.CurrentUserDto
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.data.dto.UserLoginDto

class SharedPreferences(
    val context: Context,
) : CurrentUserSharedPreferencesInterface {

    private val userCredentials =
        context.getSharedPreferences("userCredentials", Context.MODE_PRIVATE)

    private val userVersion =
        context.getSharedPreferences("userVersion", Context.MODE_PRIVATE)

    private val userCredentialsForRefresh =
        context.getSharedPreferences("userCredentialsRefreshed", Context.MODE_PRIVATE)

    override fun getCurrentUser(): Resource<CurrentUserDto> {
        val id = userCredentials.getInt("CurrentUserId", 0)
        val email = userCredentials.getString("CurrentUserEmail", "")
        val token = userCredentials.getString("CurrentUserToken", "")
        if (token.equals("")) {
            return Resource.error()
        }
        val currentUserDto = CurrentUserDto(id, email.toString(), token.toString())
        return Resource.success(currentUserDto)
    }

    override fun setCurrentUser(currentUserDto: CurrentUserDto) = userCredentials.edit()
        .putInt("CurrentUserId", currentUserDto.id)
        .putString("CurrentUserEmail", currentUserDto.email)
        .putString("CurrentUserToken", currentUserDto.token)
        .apply()

    override fun clearCurrentUser() {
        userCredentials.edit().clear().apply()
    }

    override fun getUser(): Resource<UserDto> {
        val id = userCredentials.getInt("userId", 0)
        val firstName = userCredentials.getString("userFirstName", "")
        val lastName = userCredentials.getString("userLastName", "")
        val phone = userCredentials.getString("userPhone", "")
        val email = userCredentials.getString("userEmail", "")
        val password = userCredentials.getString("userPassword", "")
        val roleName = userCredentials.getString("userRoleName", "")
        val urlLogo = userCredentials.getString("userUrlLogo", "")
        val status = userCredentials.getBoolean("userStatus", false)
        val logInTime = userCredentials.getLong("userLogInTime", 0)
        if (!status) {
            return Resource.error()
        }
        val userDto = UserDto(
            id,
            firstName.toString(),
            lastName.toString(),
            phone.toString(),
            email.toString(),
            password.toString(),
            roleName.toString(),
            urlLogo.toString(),
            status,
            logInTime
        )
        return Resource.success(userDto)
    }

    override fun setUser(userDto: UserDto) = userCredentials.edit()
        .putInt("userId", userDto.id)
        .putString("userFirstName", userDto.firstName)
        .putString("userLastName", userDto.lastName)
        .putString("userPhone", userDto.phone)
        .putString("userEmail", userDto.email)
        .putString("userPassword", userDto.password)
        .putString("userRoleName", userDto.roleName)
        .putString("userUrlLogo", userDto.urlLogo)
        .putBoolean("userStatus", userDto.status)
        .putLong("userLogInTime", userDto.logInTime)
        .apply()

    override fun clearUser() {
        userCredentials.edit().clear().apply()
    }

    override fun getUserCredentials(): Resource<UserLoginDto> {
        val email = userCredentialsForRefresh.getString("userEmail", "")
        val password = userCredentialsForRefresh.getString("userPassword", "")
        if (!email.isNullOrEmpty()) {
            return Resource.error()
        }
        val userCredentials = UserLoginDto(
            email.toString(),
            password.toString()
        )
        return Resource.success(userCredentials)
    }

    override fun setUserCredentials(userLoginDto: UserLoginDto) = userCredentialsForRefresh.edit()
        .putString("userEmail", userLoginDto.email)
        .putString("userPassword", userLoginDto.password)
        .apply()

    override fun clearUserCredentials() {
        userCredentialsForRefresh.edit().clear().apply()
    }

    override fun getCurrentApiVersion(): Int {
        return userVersion.getInt("version", 0)
    }

    override fun setCurrentApiVersion(version: Int) {
        userVersion.edit().putInt("version", version).apply()
    }

}