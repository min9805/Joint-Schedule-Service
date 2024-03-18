package com.example.joinu.calendars.controller

import com.example.joinu.calendars.dto.CalendarEventsDtoQRequest
import com.example.joinu.calendars.dto.CalendarEventsDtoResponse
import com.example.joinu.calendars.dto.CalendarListDtoResponse
import com.example.joinu.calendars.dto.CreateCalendarDtoRequest
import com.example.joinu.calendars.service.CalendarsService
import com.example.joinu.common.dto.BaseResponse
import com.example.joinu.common.exception.InvalidInputException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/calendars")
class CalendarsController(
    private val calendarsService: CalendarsService
) {
    /**
     * Get All Calendar List by CalendarListDtoResponse
     *
     * @return List of CalendarListDtoResponse
     */
    @GetMapping()
    fun getCalendars(): BaseResponse<List<CalendarListDtoResponse>> {
        val result = calendarsService.getCalendars()
        return BaseResponse(data = result)
    }

    /**
     * Get Calendar Events by List of Calendar
     */
    @PostMapping("/events")
    fun getCalendarEvents(@RequestBody calendarEventsDtoQRequest: CalendarEventsDtoQRequest): BaseResponse<MutableList<CalendarEventsDtoResponse>> {
        val calendarEvents = calendarsService.getCalendarEvents(calendarEventsDtoQRequest)
        return BaseResponse(data = calendarEvents)
    }

    /**
     * Create Calendar
     *
     * @param createCalendarDtoRequest
     * @return return result message
     */
    @PostMapping("/create")
    fun createCalendar(@RequestBody createCalendarDtoRequest: CreateCalendarDtoRequest): BaseResponse<String> {
        val result = calendarsService.createCalendar(createCalendarDtoRequest)

        return BaseResponse(message = result)
    }


}