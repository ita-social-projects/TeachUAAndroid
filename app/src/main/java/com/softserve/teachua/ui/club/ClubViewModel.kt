package com.softserve.teachua.ui.club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.*
import com.softserve.teachua.data.model.FeedbackModel
import com.softserve.teachua.data.model.MessageModel
import com.softserve.teachua.domain.interfaces.CurrentUserUseCasesInterface
import com.softserve.teachua.domain.interfaces.FeedbacksUseCasesInterface
import com.softserve.teachua.domain.interfaces.MessagesUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val feedbacksUseCasesInterface: FeedbacksUseCasesInterface,
    private val currentUserUseCasesInterface: CurrentUserUseCasesInterface,
    private val messagesUseCasesInterface: MessagesUseCasesInterface,
) :
    ViewModel() {

    private var _message = MutableStateFlow<Resource<MessageResponseDto>>(Resource.loading())

    private var _feedbacks = MutableStateFlow<Resource<FeedbacksDto>>(Resource.loading())

    private var _feedbacksList = MutableStateFlow<Resource<List<FeedbackModel>>>(Resource.loading())

    private var _user = MutableStateFlow<Resource<UserDto>>(Resource.loading())

    val feedbacks: StateFlow<Resource<FeedbacksDto>>
        get() = _feedbacks

    val feedbacksList: StateFlow<Resource<List<FeedbackModel>>>
        get() = _feedbacksList

    val message: StateFlow<Resource<MessageResponseDto>>
        get() = _message

    val user: StateFlow<Resource<UserDto>>
        get() = _user


    fun loadUser() = viewModelScope.launch {
        _user.value = Resource.loading()
        _user.value = currentUserUseCasesInterface.getUser()
    }

    fun loadFeedbacks(clubId: Int) = viewModelScope.launch {
        _feedbacksList.value = feedbacksUseCasesInterface.getFeedbacksById(clubId)
    }

    fun postFeedback(feedbacksDto: FeedbacksDto) = viewModelScope.launch {
        _feedbacks.value =
            feedbacksUseCasesInterface.postFeedback(currentUserUseCasesInterface.getCurrentUser().data?.token!!,
                feedbacksDto)
    }

    fun sendMessage(messageDto: MessageDto) = viewModelScope.launch {
        _message.value =
            messagesUseCasesInterface.sendMessage(currentUserUseCasesInterface.getCurrentUser().data?.token!!,
                messageDto)
    }

}