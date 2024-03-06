package com.example.joinu.event.entity

import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.dto.EventDtoResponse
import com.example.joinu.team.entity.Team
import com.example.joinu.member.entity.Member
import jakarta.persistence.*
import java.util.*

@Entity
class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column
    var start: Date,

    @Column
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

    fun updateEvent(eventDto: EventDto) {

        eventDto.title.let { newTitle ->
            title = newTitle
        }
        eventDto.start?.let { newStart ->
            start = newStart
        }
        eventDto.end?.let { newEnd ->
            end = newEnd
        }
        eventDto.color?.let { newData ->
            color = newData
        }
        eventDto.disabled?.let { newData ->
            disabled = newData
        }
        eventDto.editable?.let { newData ->
            editable = newData
        }
        eventDto.deletable?.let { newData ->
            deletable = newData
        }
        eventDto.allDay?.let { newData ->
            allDay = newData
        }

    }
}

@Entity
class MemberEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event,
) {

}


@Entity
class TeamEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val team: Team
) {

}
