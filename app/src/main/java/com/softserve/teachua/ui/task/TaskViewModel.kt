package com.softserve.teachua.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.TaskDto
import com.softserve.teachua.domain.interfaces.TaskUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel  @Inject constructor(private val taskUseCases: TaskUseCasesInterface) : ViewModel(){

    private var _task = MutableStateFlow<Resource<TaskDto>>(Resource.loading())

    val task : StateFlow<Resource<TaskDto>>
        get() = _task


    fun load(id : Int) = viewModelScope.launch {
        _task.value = Resource.loading()
        _task.value = taskUseCases.getTask(id)
    }

}