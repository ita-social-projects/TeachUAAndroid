package com.softserve.teachua.ui.currentNews

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
class CurrentNewsViewModel @Inject constructor(private val newsUseCasesInterface: NewsUseCasesInterface) :
    ViewModel() {

    private var _currentNews = MutableStateFlow<Resource<NewsModel>>(Resource.loading())

    val currentNews: StateFlow<Resource<NewsModel>>
        get() = _currentNews


    fun load(id: Int) = viewModelScope.launch {
        _currentNews.value = Resource.loading()
        _currentNews.value = newsUseCasesInterface.getNewsById(id)
    }
}