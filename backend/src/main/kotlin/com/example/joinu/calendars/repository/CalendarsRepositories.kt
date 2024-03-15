package com.example.joinu.calendars.repository

import com.example.joinu.calendars.entity.CalendarEvent
import com.example.joinu.calendars.entity.Calendars
import org.springframework.data.jpa.repository.JpaRepository

interface CalendarsRepository : JpaRepository<Calendars, Long> {
}

interface CalendarEventRepository : JpaRepository<CalendarEvent, Long> {
}