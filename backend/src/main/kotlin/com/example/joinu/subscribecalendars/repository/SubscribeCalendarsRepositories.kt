package com.example.joinu.subscribecalendars.repository

import com.example.joinu.subscribecalendars.entity.SubscribeCalendars
import org.springframework.data.jpa.repository.JpaRepository

interface SubscribeCalendarsRepository : JpaRepository<SubscribeCalendars, Long> {
}