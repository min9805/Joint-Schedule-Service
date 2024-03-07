package com.example.joinu.team.dto

import com.example.joinu.team.entity.Team
import com.example.joinu.team.entity.TeamEvent
import java.util.Date

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

data class GetTeamMembersDtoResponse(
    val admin_id: Long,
    val title: String,
    val mobile: String,
    val avator: String,
    val color: String,
)

data class GetTeamEventsDtoResponse(
    val event_id: Long,
    val title: String,
    val start: Date,
    val end: Date,
    val admin_id: Long,
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

data class CreateTeamEventsDtoResponse(
    val event_id: Long,
    val title: String,
    val start: Date,
    val end: Date,
    val admin_id: Long,
) {
}

data class CreateTeamEventsDtoRequest(
    val title: String,
    val start: Date,
    val end: Date,
    val admin_id: Long,
) {
    fun toEntity(): TeamEvent = TeamEvent(
        title = title,
        start = start,
        end = end,
    )
}