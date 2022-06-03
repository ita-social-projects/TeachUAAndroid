package com.softserve.teachua.ui.aboutUs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.AboutModel
import com.softserve.teachua.domain.interfaces.AboutUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel @Inject constructor(private val aboutUseCasesInterface: AboutUseCasesInterface) :
    ViewModel() {

    private var _about = MutableStateFlow<Resource<List<AboutModel>>>(Resource.loading())

    val about: StateFlow<Resource<List<AboutModel>>>
        get() = _about

    fun load() = viewModelScope.launch {
        _about.value = aboutUseCasesInterface.getAbout()
    }
    }