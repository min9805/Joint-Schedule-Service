package com.example.joinu.member.entity

import com.example.joinu.common.status.Gender
import com.example.joinu.common.status.ROLE
import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.member.dto.MemberDtoResponse
import com.example.joinu.member.dto.MemberInfoDtoResponse
import com.example.joinu.member.dto.MemberPutDtoRequest
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, length = 10)
    var name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 30)
    var email: String,

    var avator: String = "/img/default_avator.png",
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    fun toDto(): MemberDtoResponse {
        val roles: List<ROLE> = memberRole?.map { it.role } ?: emptyList()
        return MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email, roles)
    }

    fun toMemberInfoDto(): MemberInfoDtoResponse {
        return MemberInfoDtoResponse(id!!, loginId, avator)
    }

    fun encodePassword(passwordEncoder: PasswordEncoder) {
        password = passwordEncoder.encode(password)
    }

    fun updateMember(memberPutDtoRequest: MemberPutDtoRequest) {

        memberPutDtoRequest.name?.let { newName ->
            name = newName
        }
        memberPutDtoRequest.email?.let { newEmail ->
            email = newEmail
        }

        if (memberPutDtoRequest.password != null) {
            password = memberPutDtoRequest.password!!
        }

    }


}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member,

    )