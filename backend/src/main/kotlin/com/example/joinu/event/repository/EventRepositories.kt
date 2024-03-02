package com.example.joinu.event.repository

import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository


interface EventRepository : JpaRepository<Event, Long>

interface MemberEventRepository : JpaRepository<MemberEvent, Long> {
    fun findByMemberAndEvent(member: Member?, event: Event?): MemberEvent?
}