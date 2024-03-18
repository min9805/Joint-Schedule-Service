package com.example.joinu.calendars.service

import com.example.joinu.calendars.dto.CalendarEventsDtoQRequest
import com.example.joinu.calendars.dto.CalendarEventsDtoResponse
import com.example.joinu.calendars.dto.CalendarListDtoResponse
import com.example.joinu.calendars.dto.CreateCalendarDtoRequest
import com.example.joinu.calendars.entity.Calendars
import com.example.joinu.calendars.repository.CalendarEventRepository
import com.example.joinu.calendars.repository.CalendarsRepository
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.common.status.Category
import com.example.joinu.common.status.Const.DEFAULT_COLOR
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CalendarsService(
    private val calendarsRepository: CalendarsRepository,
    private val calendarsEventRepository: CalendarEventRepository,
) {

    /**
     * Get All Calendar List by CalendarListDtoResponse
     */
    fun getCalendars(): List<CalendarListDtoResponse> {
        val CalendarList = calendarsRepository.findAll()
        return CalendarList.map { it.toCalendarListDtoResponse() }
    }

    /**
     * Get Calendar Events by List of Calendar
     */
    fun getCalendarEvents(calendarEventsDtoQRequest: CalendarEventsDtoQRequest): MutableList<CalendarEventsDtoResponse> {
        val result: MutableList<CalendarEventsDtoResponse> = mutableListOf()
        calendarEventsDtoQRequest.calendar_id.forEach {
            val calendar = calendarsRepository.findById(it).orElseThrow {
                InvalidInputException("존재하지 않는 캘린더 아이디입니다.")
            }
            val responseEvents = calendar.events.map {
                it.toCalendarEventsDtoResponse()
            }

            result.addAll(responseEvents)
        }
        return result
    }

    /**
     * Create Calendar
     */
    fun createCalendar(createCalendarDtoRequest: CreateCalendarDtoRequest): String {
        val userName = (SecurityContextHolder.getContext().authentication.principal as CustomUser).username

        val calendar = Calendars(
            category = Category.valueOf(createCalendarDtoRequest.category),
            name = createCalendarDtoRequest.name,
            author = userName,
            description = createCalendarDtoRequest.description,
            color = if (createCalendarDtoRequest.color.isNotBlank()) createCalendarDtoRequest.color else DEFAULT_COLOR
        )
        val savedCalendar = calendarsRepository.save(calendar)

        createCalendarDtoRequest.events.forEach {
            val CalendarEvent = it.toEntity()
            CalendarEvent.calendars = savedCalendar
            calendarsEventRepository.save(CalendarEvent)
        }

        return "${userName} 일정이 생성되었습니다."
    }

}