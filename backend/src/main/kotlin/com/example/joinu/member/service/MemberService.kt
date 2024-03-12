package com.example.joinu.member.service

import com.example.joinu.common.authority.JwtTokenProvider
import com.example.joinu.common.authority.TokenInfo
import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.common.status.ROLE
import com.example.joinu.member.dto.*
import com.example.joinu.member.entity.Member
import com.example.joinu.member.entity.MemberRole
import com.example.joinu.member.repository.MemberRepository
import com.example.joinu.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,

    @Autowired
    private val passwordEncoder: PasswordEncoder
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        member = memberDtoRequest.toEntity()
        member.encodePassword(passwordEncoder)

        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내 정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")
        return member.toDto()
    }

    /**
     * 멤버 조회
     */
    fun searchMemberInfo(loginId: String): MemberInfoDtoResponse {
        val member: Member =
            memberRepository.findByLoginId(loginId) ?: throw InvalidInputException(
                "id",
                "회원 아이디 (${loginId})가 존재하지 않는 유저입니다."
            )
        return member.toMemberInfoDto()
    }

    /**
     * 내 정보 수정
     */
    fun saveMyInfo(memberDtoRequest: MemberPutDtoRequest): String {
        val member: Member = memberRepository.findByIdOrNull(memberDtoRequest.id) ?: throw InvalidInputException(
            "id",
            "회원번호(${memberDtoRequest.id})가 존재하지 않는 유저입니다."
        )
        member.updateMember(memberDtoRequest)
        memberRepository.save(member)
        return "수정이 완료되었습니다."
    }
}

