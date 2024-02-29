package com.example.joinu.member.repository

import com.example.joinu.event.entity.Event
import com.example.joinu.member.entity.Member
import com.example.joinu.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?

    @Query("SELECT e FROM Event e JOIN MemberEvent ue ON e.id = ue.event.id WHERE ue.member.id = :memberId")
    fun findEventsByMemberId(@Param("memberId") memberId: Long): List<Event>
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>