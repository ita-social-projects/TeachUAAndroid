package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.NewsModel

interface NewsUseCasesInterface {

    suspend fun getNews(): Resource<List<NewsModel>>

    suspend fun getNewsById(id: Int): Resource<NewsModel>
}