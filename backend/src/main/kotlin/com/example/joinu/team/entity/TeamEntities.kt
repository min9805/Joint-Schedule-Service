package com.example.joinu.team.entity

import com.example.joinu.common.status.Const
import com.example.joinu.member.entity.Member
import com.example.joinu.team.dto.*
import jakarta.persistence.*
import java.util.*
import kotlin.collections.ArrayList

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

    fun toGetTeamMembersDtoResponse(): GetTeamMembersDtoResponse = GetTeamMembersDtoResponse(
        admin_id = team.id!!,
        title = memberName,
        mobile = subName,
        avator = avator,
        color = color,
    )
}

@Entity
class TeamEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column
    var start: Date,

    @Column
    var end: Date,

    @ManyToOne
    var member: Member? = null,

    @ManyToOne
    var team: Team? = null,
) {
    fun toCreateTeamEventsDtoRequest(): CreateTeamEventsDtoResponse =
        CreateTeamEventsDtoResponse(
            event_id = id!!,
            title = title,
            start = start,
            end = end,
            admin_id = member!!.id!!
        )

    fun toGetTeamEventsDtoResponse(): GetTeamEventsDtoResponse =
        GetTeamEventsDtoResponse(
            event_id = id!!,
            title = title,
            start = start,
            end = end,
            admin_id = member!!.id!!
        )
}
