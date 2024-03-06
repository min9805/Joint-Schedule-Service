package com.example.joinu.team.entity

import com.example.joinu.event.entity.TeamEvent
import com.example.joinu.member.entity.Member
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

    fun toDto(): GetTeamsDtoResponse = GetTeamsDtoResponse(teamId = id!!, name = name)
}

@Entity
class MemberTeam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team,
) {

}