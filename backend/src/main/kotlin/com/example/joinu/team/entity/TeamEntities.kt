package com.example.joinu.team.entity

import com.example.joinu.common.status.Const
import com.example.joinu.event.entity.TeamEvent
import com.example.joinu.member.entity.Member
import com.example.joinu.team.dto.CreateTeamDtoResponse
import com.example.joinu.team.dto.GetMemberTeamDtoResponse
import com.example.joinu.team.dto.GetTeamsDtoResponse
import jakarta.persistence.*

@Entity
@Table
class Team(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    var name: String,

    ) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    val teamEvent: List<TeamEvent>? = ArrayList()

    fun toGetTeamsDtoResponse(): GetTeamsDtoResponse = GetTeamsDtoResponse(teamId = id!!, name = name)
    fun toCreateTeamDtoResponse(): CreateTeamDtoResponse = CreateTeamDtoResponse(teamId = id!!, name = name)
}

@Entity
class MemberTeam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var groupName: String,
    var memberName: String,
    var subName: String = "",
    var avator: String = "",
    var color: String = Const.DEFAULT_COLOR,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team,
) {
    fun toGetMemberTeamDtoResponse(): GetMemberTeamDtoResponse = GetMemberTeamDtoResponse(
        teamId = team.id!!,
        groupName = groupName,
        memberName = memberName,
        subName = subName,
        avator = avator,
        color = color,
    )

}