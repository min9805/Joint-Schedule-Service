package com.example.joinu.event.controller

import com.example.joinu.common.dto.BaseResponse
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.dto.EventDtoResponse
import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.event.repository.EventRepository
import com.example.joinu.event.repository.MemberEventRepository
import com.example.joinu.event.service.EventService
import com.example.joinu.member.repository.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 이벤트 생성 컨트롤러
@RestController
@RequestMapping("/api/event")
class EventController(
    private var eventService: EventService,
) {


    @PostMapping("/create")
    fun createEvent(
        @RequestBody eventDto: EventDto,
    ): BaseResponse<Event> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = eventService.create(eventDto, userId)
        return BaseResponse(data = result)
    }

    @PostMapping("/update")
    fun updateEvent(
        @RequestBody eventDto: EventDto,
    ): BaseResponse<Event> {
        val updateEvents = eventService.updateEvents(eventDto)
        return BaseResponse(data = updateEvents)
    }

    @GetMapping("/")
    fun getEvents(): BaseResponse<List<EventDtoResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val data = eventService.getEvents(userId)
        return BaseResponse(data = data)
    }
}
