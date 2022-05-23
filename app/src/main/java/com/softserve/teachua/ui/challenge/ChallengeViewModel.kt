package com.softserve.teachua.ui.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.ChallengeModel
import com.softserve.teachua.domain.interfaces.ChallengesUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(private val challengesUseCasesInterface: ChallengesUseCasesInterface) : ViewModel() {
    private var _challenge = MutableStateFlow<Resource<ChallengeModel>>(Resource.loading())

    val challenge : StateFlow<Resource<ChallengeModel>>
        get() = _challenge


    fun load(id : Int) = viewModelScope.launch {
        _challenge.value = Resource.loading()
        _challenge.value = challengesUseCasesInterface.getChallenge(id)
    }
}