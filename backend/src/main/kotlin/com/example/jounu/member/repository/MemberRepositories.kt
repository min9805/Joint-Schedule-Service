package com.example.jounu.member.repository

import com.example.jounu.member.entity.Member
import com.example.jounu.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>