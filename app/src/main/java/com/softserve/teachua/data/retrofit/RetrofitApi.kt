package com.softserve.teachua.data.retrofit

import com.softserve.teachua.data.dto.*

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    @GET("banners")
    suspend fun getAllBanners(
    ): Response<List<BannersDto>>

    @GET("newslist")
    suspend fun getAllNews(
    ): Response<List<NewsDto>>

    @GET("news/{id}")
    suspend fun getNewsById(
        @Path("id")
        id: Int,
    ): Response<NewsDto>

    @GET("user/{userId}")
    suspend fun getUserById(
        @Header("Authorization")
        authHeader: String,
        @Path("userId")
        userId: Int? = null,
    ): Response<UserDto>

    @GET("categories")
    suspend fun getAllCategories(
    ): Response<List<CategoryDto>>

    @GET("cities")
    suspend fun getAllCities(
    ): Response<List<CitiesDto>>

    @GET("districts/{cityName}")
    suspend fun getDistrictsByCityName(
        @Path("cityName")
        cityName: String? = null,
    ): Response<List<DistrictsDto>>

    @GET("stations/{cityName}")
    suspend fun getStationsByCityName(
        @Path("cityName")
        cityName: String? = null,
    ): Response<List<StationsDto>>


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


    @JvmSuppressWildcards
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

    @GET("challenge/{id}")
    suspend fun getChallengeById(@Path("id") id: Int): Response<ChallengeDto>

    //?active=true - added this as on site, so we get only active challenges
    @GET("challenges?active=true")
    suspend fun getChallenges(): Response<List<ChallengeDto>>

    @GET("challenge/task/{id}")
    suspend fun getTask(@Path("id") id: Int): Response<TaskDto>

    @GET("about/{id}")
    suspend fun getAboutById(@Path("id") id: Int): Response<AboutDto>

    @GET("about")
    suspend fun getAbout(): Response<List<AboutDto>>


    @POST("signin")
    suspend fun getLoggedUser(
        @Body
        userLoginDto: UserLoginDto,
    ): Response<UserLoggedDto>

}