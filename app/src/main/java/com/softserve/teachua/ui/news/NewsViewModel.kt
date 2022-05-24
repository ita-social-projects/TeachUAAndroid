package com.softserve.teachua.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.NewsModel
import com.softserve.teachua.domain.interfaces.NewsUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCasesInterface: NewsUseCasesInterface) :
    ViewModel() {

    private var _news = MutableStateFlow<Resource<List<NewsModel>>>(Resource.loading())

    val news: StateFlow<Resource<List<NewsModel>>>
        get() = _news

    fun load() = viewModelScope.launch {
        _news.value = newsUseCasesInterface.getNews()
    }

}