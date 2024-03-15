package com.example.joinu.calendars.entity

import com.example.joinu.common.status.Category
import com.example.joinu.member.entity.Member
import jakarta.persistence.*

/**
 * 구독 가능한 캘린더의 목록
 * @property id
 * @property category 캘린더의 카테고리 (학교, 스포츠, 공부, 일상 등..)
 * @property name 해당 캘린더의 제목
 * @property author 해당 캘린더의 작성자
 *
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
) {}