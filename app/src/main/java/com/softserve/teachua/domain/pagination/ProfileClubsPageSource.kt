package com.softserve.teachua.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.softserve.teachua.app.tools.toClub
import com.softserve.teachua.data.model.ClubModel
import com.softserve.teachua.data.retrofit.RetrofitApi
import retrofit2.HttpException

class ProfileClubsPageSource(
    private val service: RetrofitApi,
    private var profileId: Int,
) : PagingSource<Int, ClubModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClubModel> {
        val page = params.key ?: 0
        val pageSize = 3

        val profileClubsResponse = service.getClubsByUserId(profileId, page)
        return if (profileClubsResponse.isSuccessful) {
            var profileClubs = checkNotNull(profileClubsResponse.body()).content.map { it.toClub() }
            val nextKey = if (profileClubs.size < (pageSize) - 2) null else page + 1
            val prevKey = if (page == 0) null else page - 1

            LoadResult.Page(profileClubs, prevKey, nextKey)
        } else
            LoadResult.Error(HttpException(profileClubsResponse))

    }


    override fun getRefreshKey(state: PagingState<Int, ClubModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}