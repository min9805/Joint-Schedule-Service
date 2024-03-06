package com.example.joinu.team.repository

import com.example.joinu.team.entity.MemberTeam
import com.example.joinu.team.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository : JpaRepository<Team, Long> {
}

interface MemberTeamRepository : JpaRepository<MemberTeam, Long> {
    fun findByMemberId(memberId: Long): List<MemberTeam>

}