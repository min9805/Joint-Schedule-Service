package com.example.joinu.calendars.dto

import com.example.joinu.calendars.entity.CalendarEvent
import com.example.joinu.common.status.Category
import java.util.Date

data class CalendarListDtoResponse(
    val category: Category,
    val name: String,
    val author: String
)

data class CalendarEventsDtoQRequest(
    val calendar_id: List<Long>
)

data class CalendarEventsDtoResponse(
    val title: String,
    val description: String,
    val start: Date,
    val end: Date,
    val calendar: String = "",
)

data class CreateCalendarDtoRequest(
    val name: String,
    val category: String,
    val author: String,
    val color: String,
    val events: List<CreateCalendarEventsDto>
)

data class CreateCalendarEventsDto(
    val eventId: Int,
    val title: String,
    val description: String,
    val start: Date,
    val end: Date,
) {
    fun toEntity(): CalendarEvent = CalendarEvent(
        title = title,
        description = description,
        start = start,
        end = end
    )
}