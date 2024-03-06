package com.example.joinu.event.service

import com.example.joinu.common.authority.JwtTokenProvider
import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.dto.EventDtoResponse
import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.event.repository.EventRepository
import com.example.joinu.event.repository.MemberEventRepository
import com.example.joinu.member.dto.*
import com.example.joinu.member.entity.Member
import com.example.joinu.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class EventService(
    private val eventRepository: EventRepository,
    private val memberEventRepository: MemberEventRepository,
    private val memberRepository: MemberRepository,

    ) {
    /**
     * 이벤트 생성
     */
    fun create(eventDto: EventDto, id: Long): Event {

        //Id 검사
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")

        val event = eventDto.toEntity()
        val newEvent = eventRepository.save(event)

        val memberEvent = MemberEvent(member = member, event = event)
        memberEventRepository.save(memberEvent)

        return newEvent
    }

    /**
     * 이벤트 조회
     */
    fun getEvents(id: Long): List<EventDtoResponse>? {
//        val result = memberRepository.findEventsByMemberId(id)
        val memberEvent = memberEventRepository.findByMemberId(id)

        val result = memberRepository.findEventsByMemberId(id)
        return result.map { it.toDto() }
    }


    /**
     * 이벤트 수정
     */
    fun updateEvents(eventDto: EventDto): Event {
        val event: Event = eventRepository.findByIdOrNull(eventDto.event_id) ?: throw InvalidInputException(
            "event_id 가 잘못되었습니다",
        )
        event.updateEvent(eventDto)
        val updatedEvent = eventRepository.save(event)
        return updatedEvent
    }

    /**
     * 이벤트 삭제
     */
    fun deleteEvents(deleteId: Long, memberId: Long): String {
        val member: Member =
            memberRepository.findByIdOrNull(memberId) ?: throw InvalidInputException(
                "id",
                "회원번호(${memberId})가 존재하지 않는 유저입니다."
            )

        val event: Event = eventRepository.findByIdOrNull(deleteId) ?: throw InvalidInputException(
            "event_id 가 잘못되었습니다",
        )

        val findByMemberAndEvent =
            memberEventRepository.findByMemberAndEvent(member, event) ?: throw InvalidInputException(
                "memberId 혹은 eventId 가 잘못되었습니다."
            )
        findByMemberAndEvent.id?.let { memberEventRepository.deleteById(it) }
        eventRepository.deleteById(deleteId)

        return "${deleteId} 이벤트가 삭제되었습니다."
    }
}

