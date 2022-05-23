package com.softserve.teachua.data.model

import com.softserve.teachua.data.dto.TaskDto
import com.softserve.teachua.data.dto.UserDto

data class ChallengeModel(
    val description: String = "",
    val id: Int,
    val isActive: Boolean = false,
    val name: String = "",
    val picture: String = "",
    val registrationLink: String ="",
    val sortNumber: Int = 1,
    val tasks: List<TaskDto> = emptyList(),
    val title: String = "",
    val user: UserDto?
)
