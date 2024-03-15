package com.example.joinu.subscribecalendars.entity

import com.example.joinu.calendars.entity.Calendars
import com.example.joinu.common.status.Category
import com.example.joinu.common.status.Gender
import com.example.joinu.member.entity.Member
import jakarta.persistence.*

/**
 * 멤버가 구독하고 있는 캘린더 엔티티
 * @property id
 * @property member 구독하고 있는 해당 멤버
 * @property calendars 구독하는 캘린더
 * @property alias 구독하는 캘린더의 별칭
 */
@Entity
class SubscribeCalendars(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendars_id")
    val calendars: Calendars? = null,

    var alias: String,

    ) {
}