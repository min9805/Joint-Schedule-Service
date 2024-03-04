package com.example.joinu.event.service

import com.example.joinu.common.status.Gender
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.event.repository.EventRepository
import com.example.joinu.event.repository.MemberEventRepository
import com.example.joinu.member.entity.Member
import com.example.joinu.member.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class EventServiceTest {

    @Mock
    private lateinit var eventRepository: EventRepository

    @Mock
    private lateinit var memberEventRepository: MemberEventRepository

    @Mock
    private lateinit var memberRepository: MemberRepository

    @InjectMocks
    private lateinit var eventService: EventService

    @Test
    fun `test create event`() {
        //given
        val member = Member(
            loginId = "test_user",
            password = "password123!",
            name = "Test User",
            birthDate = LocalDate.now(),
            gender = Gender.MAN,
            email = "test@example.com"
        )
        val eventDto = EventDto(
            event_id = null,
            title = "Test Event",
            start = Date(),
            end = Date(),
            color = "#FF0000",
            disabled = false,
            editable = true,
            deletable = true,
            allDay = false
        )


        //when
        val savedMember = memberRepository.save(member)
        val savedMemberId = savedMember.id
        eventService.create(eventDto, savedMemberId!!)
        val eventList = memberRepository.findEventsByMemberId(savedMemberId)

        //then
        assertTrue(eventList.isNotEmpty())
        assertTrue(eventList.any { it.title == eventDto.title })
    }
}
