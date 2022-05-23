package com.softserve.teachua.ui.challenges

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
class ChallengesViewModel @Inject constructor(private val challengesUseCasesInterface: ChallengesUseCasesInterface) : ViewModel() {

    private var _challenges = MutableStateFlow<Resource<List<ChallengeModel>>>(Resource.loading())

    val challenges : StateFlow<Resource<List<ChallengeModel>>>
        get() = _challenges

    fun load() = viewModelScope.launch {
        _challenges.value = challengesUseCasesInterface.getChallenges()
    }
}