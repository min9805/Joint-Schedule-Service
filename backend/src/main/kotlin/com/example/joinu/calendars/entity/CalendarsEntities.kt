package com.example.joinu.calendars.entity

import com.example.joinu.common.status.Category
import com.example.joinu.member.entity.Member
import com.example.joinu.team.entity.Team
import jakarta.persistence.*
import java.util.*

/**
 * Repersents a calendar that can be subscribed.
 * @property id Identitier for the calendar
 * @property category Category of the calendar (school, sports, study, daily, etc..)
 * @property name The name of calendar
 * @property author The Author name (member login Id) of calendar
 * @property events Events belong the calendar
 */
@Entity
class Calendars(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    val category: Category,

    val name: String,

    val author: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendars")
    val events: List<CalendarEvent> = ArrayList(),
) {


}

/**
 * Represents an event in a calendar.
 * @property id The unique identifier for the event.
 * @property calendars The id of Calendar these events belong to.
 * @property title The title of the event.
 * @property description The description of the event.
 * @property start The start date and time of the event.
 * @property end The end date and time of the event.
 */
@Entity
class CalendarEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val calendars: Calendars? = null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column
    var description: String,

    @Column
    var start: Date,

    @Column
    var end: Date,
) {}