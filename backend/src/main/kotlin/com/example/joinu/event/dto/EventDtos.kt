package com.example.joinu.event.dto

import com.example.joinu.common.status.Const
import com.example.joinu.event.entity.Event
import com.example.joinu.member.entity.Member
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

//
//import com.example.joinu.common.annotation.ValidEnum
//import com.example.joinu.common.status.Gender
//import com.example.joinu.common.status.ROLE
//import com.example.joinu.member.entity.Member
//import com.fasterxml.jackson.annotation.JsonProperty
//import jakarta.validation.constraints.Email
//import jakarta.validation.constraints.NotBlank
//import jakarta.validation.constraints.Pattern
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//


data class EventDto(
    @JsonProperty("event_id")
    var event_id: Long,

    @JsonProperty("title")
    var title: String,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("start")
    var start: Date,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("end")
    var end: Date,

    @JsonProperty("color")
    var color: String?,

    @JsonProperty("disabled")
    var disabled: Boolean?,

    @JsonProperty("editable")
    var editable: Boolean?,

    @JsonProperty("deletable")
    var deletable: Boolean?,

    @JsonProperty("allDay")
    var allDay: Boolean?,
) {
    fun toEntity(): Event =
        Event(
            title = title,
            start = start,
            end = end,
            color = color ?: Const.DEFAULT_COLOR,
            disabled = disabled ?: Const.DEFAULT_DISABLED,
            editable = editable ?: Const.DEFAULT_EDITABLE,
            deletable = deletable ?: Const.DEFAULT_DELETABLE,
            allDay = allDay ?: Const.DEFAULT_ALL_DAY
        )

}

data class EventDtoResponse(
    val event_id: Long,
    val title: String,
    val start: Date,
    val end: Date,
    val disabled: Boolean?,
    val color: String?,
    val editable: Boolean?,
    val deletable: Boolean?,
    val allDay: Boolean?,
)

data class DeleteEventDto(
    val deletedId: Long
)