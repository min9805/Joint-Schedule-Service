package com.example.joinu.event.service

import com.example.joinu.common.authority.JwtTokenProvider
import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.dto.EventDtoResponse
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
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,

    ) {
    /**
     * 이벤트 생성
     */
    fun create(eventDto: EventDto, id: Long): String {

        //Id 검사
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")

        val event = eventDto.toEntity()
        eventRepository.save(event)

        val memberEvent = MemberEvent(member = member, event = event)
        memberEventRepository.save(memberEvent)

        return "이벤트 생성이 완료되었습니다."
    }

    /**
     * 이벤트 조회
     */
    fun getEvents(id: Long): List<EventDtoResponse>? {
        val result = memberRepository.findEventsByMemberId(id)
        return result.map { it.toDto() }
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    fun saveMyInfo(memberDtoRequest: MemberPutDtoRequest): String {
        val member: Member = memberRepository.findByIdOrNull(memberDtoRequest.id) ?: throw InvalidInputException(
            "id",
            "회원번호(${memberDtoRequest.id})가 존재하지 않는 유저입니다."
        )
        member.updateMember(memberDtoRequest)
        memberRepository.save(member)
        return "수정이 완료되었습니다."
    }
}

