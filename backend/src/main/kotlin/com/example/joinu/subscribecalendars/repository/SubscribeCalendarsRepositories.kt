package com.example.joinu.subscribecalendars.repository

import com.example.joinu.subscribecalendars.entity.SubscribeCalendars
import com.example.joinu.team.entity.MemberTeam
import org.springframework.data.jpa.repository.JpaRepository

interface SubscribeCalendarsRepository : JpaRepository<SubscribeCalendars, Long> {
    fun deleteByMemberIdAndCalendarsId(memberId: Long, calendarsId: Long): Long

    fun findByMemberId(memberId: Long): List<SubscribeCalendars>

}