package com.example.joinu.team.dto

import com.example.joinu.team.entity.Team

data class GetTeamsDtoResponse(
    val teamId: Long,
    val name: String,
)


data class CreateTeamDtoRequest(
    val name: String
) {
    fun toEntity(): Team = Team(name = name)
}