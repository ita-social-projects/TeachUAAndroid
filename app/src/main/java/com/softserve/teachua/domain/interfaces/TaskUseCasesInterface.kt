package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.TaskDto

interface TaskUseCasesInterface {

    suspend fun getTask(id : Int) : Resource<TaskDto>

}