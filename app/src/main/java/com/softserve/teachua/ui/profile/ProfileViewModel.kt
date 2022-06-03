package com.softserve.teachua.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.UserDto
import com.softserve.teachua.data.retrofit.Common
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface
import com.softserve.teachua.domain.pagination.ProfileClubsPageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val currentUserUseCases: CurrentUserUseCasesInterface) :
    ViewModel() {

    private var _currentUserProfile = MutableStateFlow<Resource<UserDto>>(Resource.loading())

    val currentUserProfile: StateFlow<Resource<UserDto>>
        get() = _currentUserProfile


    fun loadUser() =
        viewModelScope.launch {
            _currentUserProfile.value = Resource.loading()
            _currentUserProfile.value = currentUserUseCases.getUser()

        }

    val profileClubs = Pager(config = PagingConfig(pageSize = 3), pagingSourceFactory = {
        ProfileClubsPageSource(Common.retrofitService,
            currentUserProfile.value.data?.id!!)
    }).flow.cachedIn(viewModelScope)


}