package com.example.jounu.member.entity

import com.example.jounu.common.status.Gender
import com.example.jounu.common.status.ROLE
import com.example.jounu.member.dto.MemberDtoResponse
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])])
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 30)
    val email: String,
) {
    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMDD"))

    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null
}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member,
)