package com.example.joinu.event.entity

import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.dto.EventDtoResponse
import com.example.joinu.member.entity.Member
import com.example.joinu.member.entity.MemberRole
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var start: Date,

    @Column
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var end: Date,

    @Column
    var disabled: Boolean? = null,

    @Column(length = 30)
    var color: String? = null,

    @Column
    var editable: Boolean? = null,

    @Column
    var deletable: Boolean? = null,

    @Column
    var allDay: Boolean? = null,
) {
    fun toDto(): EventDtoResponse {
        return EventDtoResponse(id!!, title, start, end, disabled, color, editable, deletable, allDay)
    }
}

@Entity
class MemberEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event,
) {

}