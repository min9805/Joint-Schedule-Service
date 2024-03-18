package com.example.joinu.subscribecalendars.service

import com.example.joinu.calendars.entity.Calendars
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.member.entity.Member
import com.example.joinu.subscribecalendars.dto.SubscribeRequestDto
import com.example.joinu.subscribecalendars.dto.UnbscribeRequestDto
import com.example.joinu.subscribecalendars.entity.SubscribeCalendars
import com.example.joinu.subscribecalendars.repository.SubscribeCalendarsRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SubscribeCalendarsServices(
    private val subscribeCalendarsRepository: SubscribeCalendarsRepository
) {
    /**
     * Subscribe the Calendar
     *
     * @param SubscribeRequestDto
     * @return String message
     */
    fun subscribeCalendar(subscribeRequestDto: SubscribeRequestDto): String {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val member = Member(id = userId)
        val calendars = Calendars(id = subscribeRequestDto.id)

        val subscribeCalendars = SubscribeCalendars(
            member = member,
            calendars = calendars,
            alias = subscribeRequestDto.alias,
            color = subscribeRequestDto.color
        )
        subscribeCalendarsRepository.save(subscribeCalendars)

        return "일정이 구독되었습니다."
    }

    /**
     * Unsubscribing calendar
     *
     *  @param UnbscribeRequestDto
     *  @return String message
     */
    fun unsubscribeCalendar(unbscribeRequestDto: UnbscribeRequestDto): String {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

        subscribeCalendarsRepository.deleteByMemberIdAndCalendarsId(userId, unbscribeRequestDto.id)

        return "일정 구독이 취소되었습니다."
    }
}