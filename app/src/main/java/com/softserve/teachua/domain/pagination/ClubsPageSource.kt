package com.softserve.teachua.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softserve.teachua.data.dto.AdvancedSearchClubDto
import com.softserve.teachua.data.dto.SearchClubDto
import com.softserve.teachua.app.tools.toClub
import com.softserve.teachua.data.model.ClubModel
import com.softserve.teachua.data.retrofit.RetrofitApi
import retrofit2.HttpException

class ClubsPageSource(
    private val service: RetrofitApi,
    private var searchClubDto: SearchClubDto,
    private var advancedSearchClubDto: AdvancedSearchClubDto,
) : PagingSource<Int, ClubModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClubModel> {
        // println("length " + MainActivity().cityName.length)
        val clubName = ""
        var cityName = searchClubDto.cityName
        val isOnline = false
        val categoryName = ""
        val page = params.key ?: 0
        val pageSize = 8

        println(advancedSearchClubDto.categoriesName)
        println("in datasource " + searchClubDto.cityName)
        println("in datasource" + searchClubDto.cityName.length)

        if (cityName.isEmpty()) {
            cityName = "Київ"
        }


        when (advancedSearchClubDto.isAdvanced) {

            true -> {

                println("ADVANCED")

                val advancedSearchResponse =
                    service.getClubsByAdvancedSearch(
                        name = advancedSearchClubDto.name,
                        cityName = advancedSearchClubDto.cityName,
                        districtName = advancedSearchClubDto.districtName,
                        stationName = advancedSearchClubDto.stationName,
                        sort = advancedSearchClubDto.sort,
                        categoriesName = advancedSearchClubDto.categoriesName,
                        isCenter = advancedSearchClubDto.isCenter,
                        page = page

                    )
                if (advancedSearchResponse.isSuccessful) {

                    val searchedClubs =
                        checkNotNull(advancedSearchResponse.body()).content.map { it.toClub() }

                    println(page)
                    val nextKey = if (searchedClubs.size < (pageSize) - 2) null else page + 1
                    val prevKey = if (page == 0) null else page - 1
                    return LoadResult.Page(searchedClubs, prevKey, nextKey)
                } else {
                    println(advancedSearchResponse.errorBody())
                    return LoadResult.Error(HttpException(advancedSearchResponse))
                }

            }

            false -> {

                println("DEFAULT")

                val searchResponse =
                    service.getAllClubs(clubName = clubName,
                        cityName = cityName,
                        isOnline = isOnline,
                        categoryName = categoryName,
                        page = page)



                if (searchResponse.isSuccessful) {
                    val clubs = checkNotNull(searchResponse.body()).content.map { it.toClub() }
                    println(searchResponse.body()!!.content[0].categories)
                    println(page)
                    val nextKey = if (clubs.size < pageSize) null else page + 1
                    val prevKey = if (page == 0) null else page - 1
                    return LoadResult.Page(clubs, prevKey, nextKey)
                } else {
                    println(searchResponse.errorBody().toString())
                    return LoadResult.Error(HttpException(searchResponse))
                }


            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ClubModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}