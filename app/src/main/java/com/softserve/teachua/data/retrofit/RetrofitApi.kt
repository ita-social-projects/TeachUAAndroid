package com.softserve.teachua.data.retrofit

import com.softserve.teachua.data.dto.*

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    //BANNERS
    @GET("banners")
    suspend fun getAllBanners(
    ): Response<List<BannersDto>>

    //NEWS

    @GET("newslist")
    suspend fun getAllNews(
    ): Response<List<NewsDto>>

    @GET("news/{id}")
    suspend fun getNewsById(
        @Path("id")
        id: Int,
    ): Response<NewsDto>

    //USER
    @GET("user/{userId}")
    suspend fun getUserById(
        @Header("Authorization")
        authHeader: String,
        @Path("userId")
        userId: Int? = null,
    ): Response<UserDto>

    @PUT("user/{userId}")
    suspend fun updateUserById(
        @Header("Authorization")
        authHeader: String,
        @Path("userId")
        userId: Int? = null,
        @Body
        updateUserDto: UpdateUserDto,
    ): Response<UpdateUserDto>

    //CATEGORIES
    @GET("categories")
    suspend fun getAllCategories(
    ): Response<List<CategoryDto>>

    //CITIES
    @GET("cities")
    suspend fun getAllCities(
    ): Response<List<CitiesDto>>

    //DISTRICTS
    @GET("districts/{cityName}")
    suspend fun getDistrictsByCityName(
        @Path("cityName")
        cityName: String? = null,
    ): Response<List<DistrictsDto>>

    //STATIONS
    @GET("stations/{cityName}")
    suspend fun getStationsByCityName(
        @Path("cityName")
        cityName: String? = null,
    ): Response<List<StationsDto>>


    //CLUBS
    @GET("clubs/search?")
    suspend fun getAllClubs(
        @Query("clubName")
        clubName: String? = "",
        @Query("cityName")
        cityName: String? = "Київ",
        @Query("isOnline")
        isOnline: Boolean? = false,
        @Query("categoryName")
        categoryName: String? = "",
        @Query("page")
        page: Int? = 0,
    ): Response<ClubsDto>


    @GET("clubs/search/advanced?")
    suspend fun getClubsByAdvancedSearch(
        @Query("name")
        name: String? = "",
        @Query("age")
        age: String? = null,
        @Query("cityName")
        cityName: String? = "Київ",
        @Query("districtName")
        districtName: String? = null,
        @Query("stationName")
        stationName: String? = null,
        @Query("categoriesName", encoded = true)
        categoriesName: List<String>? = null,
        @Query("isCenter")
        isCenter: Boolean? = null,
        @Query("isOnline")
        isOnline: Boolean? = null,
        @Query("sort")
        sort: String? = "name,asc",
        @Query("page")
        page: Int? = 0,
    ): Response<ClubsDto>

    @GET("clubs/{id}?")
    suspend fun getClubsByUserId(
        @Path("id")
        id: Int,
        @Query("page")
        page: Int? = 0,
    ): Response<ClubsDto>

    //CHALLENGES
    @GET("challenge/{id}")
    suspend fun getChallengeById(@Path("id") id: Int): Response<ChallengeDto>

    //?active=true - added this as on site, so we get only active challenges
    @GET("challenges?active=true")
    suspend fun getChallenges(): Response<List<ChallengeDto>>

    @GET("challenge/task/{id}")
    suspend fun getTask(@Path("id") id: Int): Response<TaskDto>

    //ABOUT
    @GET("about/{id}")
    suspend fun getAboutById(@Path("id") id: Int): Response<AboutDto>

    @GET("about")
    suspend fun getAbout(): Response<List<AboutDto>>


    //LOG-IN
    @POST("signin")
    suspend fun getLoggedUser(
        @Body
        userLoginDto: UserLoginDto,
    ): Response<UserLoggedDto>

    // FEEDBACKS
    @GET("feedbacks/{id}")
    suspend fun getFeedbacksById(@Path("id") id: Int): Response<ArrayList<FeedbacksDto>>

    //MESSAGES
    @POST("message")
    suspend fun sendMessage(
        @Header("Authorization")
        authHeader: String,
        @Body
        messageDto: MessageDto,
    ): Response<MessageResponseDto>

    @GET("messages/recipient/{id}")
    suspend fun getMessagesByRecipientId(
        @Header("Authorization")
        authHeader: String,
        @Path("id")
        id: Int,
    ): Response<List<MessageResponseDto>>
}