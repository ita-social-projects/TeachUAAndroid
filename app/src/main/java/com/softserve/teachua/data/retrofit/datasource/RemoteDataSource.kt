package com.softserve.teachua.data.retrofit.datasource

import com.softserve.teachua.data.dto.MessageDto
import com.softserve.teachua.data.dto.UpdateUserDto
import com.softserve.teachua.data.dto.UserLoginDto
import com.softserve.teachua.data.retrofit.RetrofitApi
import javax.inject.Inject

//
//
//ALWAYS USE THIS
//
//

class RemoteDataSource @Inject constructor(private val retrofitApi: RetrofitApi) :
    BaseDataSource() {

    suspend fun getChallengeById(id: Int) = getResult { retrofitApi.getChallengeById(id) }

    suspend fun getChallenges() = getResult { retrofitApi.getChallenges() }

    suspend fun getNewsById(id: Int) = getResult { retrofitApi.getNewsById(id) }

    suspend fun getNews() = getResult { retrofitApi.getAllNews() }

    suspend fun getAboutById(id: Int) = getResult { retrofitApi.getAboutById(id) }

    suspend fun getAbout() = getResult { retrofitApi.getAbout() }

    suspend fun getTask(id: Int) = getResult { retrofitApi.getTask(id) }

    suspend fun getFeedbacksById(clubId: Int) = getResult { retrofitApi.getFeedbacksById(clubId) }

    suspend fun getMessagesByRecipientId(token: String, recipientId: Int) =
        getResult { retrofitApi.getMessagesByRecipientId(token, recipientId) }

    suspend fun sendMessage(token: String, messageDto: MessageDto) =
        getResult { retrofitApi.sendMessage(token, messageDto) }

    suspend fun getUserById(token: String, id: Int) =
        getResult { retrofitApi.getUserById(token, id) }

    suspend fun updateUserById(token: String, id: Int, updateUserDto: UpdateUserDto) =
        getResult { retrofitApi.updateUserById(token, id, updateUserDto) }

    suspend fun getAllBanners() = getResult { retrofitApi.getAllBanners() }

    suspend fun getAllCategories() = getResult { retrofitApi.getAllCategories() }

    suspend fun getAllCities() = getResult { retrofitApi.getAllCities() }

    suspend fun getLoggedUser(userLoginDto: UserLoginDto) =
        getResult { retrofitApi.getLoggedUser(userLoginDto) }

    suspend fun getAllDistricts(cityName: String) =
        getResult { retrofitApi.getDistrictsByCityName(cityName) }

    suspend fun getAllStations(cityName: String) =
        getResult { retrofitApi.getStationsByCityName(cityName) }

}