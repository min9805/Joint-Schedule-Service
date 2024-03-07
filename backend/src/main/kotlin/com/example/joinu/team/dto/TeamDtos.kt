package com.example.joinu.team.dto

import com.example.joinu.team.entity.Team

data class GetTeamsDtoResponse(
    val teamId: Long,
    val name: String,
)

data class GetMemberTeamDtoResponse(
    val teamId: Long,
    val groupName: String,
    val memberName: String,
    val subName: String,
    val avator: String,
    val color: String,
)

data class CreateTeamDtoRequest(
    val name: String
) {
    fun toEntity(): Team = Team(name = name)
}

data class CreateTeamDtoResponse(
    val name: String,
    val teamId: Long,
) {
}