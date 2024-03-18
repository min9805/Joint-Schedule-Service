package com.example.joinu.subscribecalendars.dto

/**
 * This DTO transfer request data for subscribe the calendar.
 *
 * @property id Id for calendar that member wants to subscribe
 * @property alias Alias for calendar by member
 * @property color Color for Calendar Events
 */
data class SubscribeRequestDto(
    val id: Long,
    val alias: String,
    val color: String,
)

/**
 * This DTO transfer request data for unsubscribe the calendar.
 *
 * @property id Id for calendar that member wants to subscribe
 */
data class UnbscribeRequestDto(
    val id: Long,
)
