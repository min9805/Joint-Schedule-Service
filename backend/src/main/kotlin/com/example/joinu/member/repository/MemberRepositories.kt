package com.example.JoinU.member.repository

import com.example.JoinU.member.entity.Member
import com.example.JoinU.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>