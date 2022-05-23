package com.softserve.teachua.data.dto


data class ChallengeDto(
    val description: String,
    val id: Int,
    val isActive: Boolean,
    val name: String,
    val picture: String,
    val registrationLink: String,
    val sortNumber: Int,
    val tasks: List<TaskDto>,
    val title: String,
    val user: UserDto
)
