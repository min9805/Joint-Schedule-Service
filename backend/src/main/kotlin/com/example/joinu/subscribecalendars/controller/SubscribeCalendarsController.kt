package com.example.joinu.subscribecalendars.controller

import com.example.joinu.calendars.entity.Calendars
import com.example.joinu.common.dto.BaseResponse
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.member.entity.Member
import com.example.joinu.subscribecalendars.dto.SubscribeRequestDto
import com.example.joinu.subscribecalendars.dto.UnbscribeRequestDto
import com.example.joinu.subscribecalendars.entity.SubscribeCalendars
import com.example.joinu.subscribecalendars.service.SubscribeCalendarsServices
import com.fasterxml.jackson.databind.ser.Serializers.Base
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subscribe")
class SubscribeCalendarsController(
    private val subscribeCalendarsServices: SubscribeCalendarsServices
) {
    /**
     * Subscribe the Calendar
     *
     * @param SubscribeRequestDto
     * @return String message
     */
    @PostMapping("/subscribe")
    fun subscribeCalendar(@RequestBody subscribeRequestDto: SubscribeRequestDto): BaseResponse<String> {
        val result = subscribeCalendarsServices.subscribeCalendar(subscribeRequestDto)
        return BaseResponse(message = result)
    }

    /**
     * Unsubscribing calendar
     *
     *  @param UnbscribeRequestDto
     *  @return String message
     */
    @PostMapping("/unsubscribe")
    fun unsubscribeCalendar(@RequestBody unbscribeRequestDto: UnbscribeRequestDto): BaseResponse<String> {
        val result = subscribeCalendarsServices.unsubscribeCalendar(unbscribeRequestDto)

        return BaseResponse(message = result)
    }

    /**
     * Get List of Subscribed calendar
     *
     *  @return Subscribe Calendar List
     */
    @GetMapping()
    fun getSubscribeList(): BaseResponse<List<Long?>> {
        val result = subscribeCalendarsServices.getSubscribeList()
        return BaseResponse(data = result)
    }
}