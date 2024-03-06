package com.example.joinu.member.service

import com.example.joinu.common.authority.JwtTokenProvider
import com.example.joinu.common.status.Gender
import com.example.joinu.common.status.ROLE
import com.example.joinu.event.dto.EventDto
import com.example.joinu.member.dto.MemberDtoRequest
import com.example.joinu.member.dto.MemberDtoResponse
import com.example.joinu.member.entity.Member
import com.example.joinu.member.entity.MemberRole
import com.example.joinu.member.repository.MemberRepository
import com.example.joinu.member.repository.MemberRoleRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.be
import io.kotest.matchers.should
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate
import java.util.*
import kotlin.collections.List


private var memberRepository: MemberRepository = mockk(relaxed = true)
private var memberRoleRepository: MemberRoleRepository = mockk(relaxed = true)
private var authenticationManagerBuilder: AuthenticationManagerBuilder = mockk()
private var jwtTokenProvider: JwtTokenProvider = mockk()
private var passwordEncoder: PasswordEncoder = mockk(relaxed = true)


@InjectMockKs
var memberService: MemberService = MemberService(
    memberRepository,
    memberRoleRepository,
    authenticationManagerBuilder,
    jwtTokenProvider,
    passwordEncoder
)


class MemberServiceTest : BehaviorSpec({

    Given("회원가입 테스트") {
        val memberSignupDto = MemberDtoRequest(
            id = null,
            _loginId = "테스트_로그인_아이디",
            _password = "password123!",
            _name = "테스트_이름",
            _birthDate = LocalDate.now().toString(),
            _gender = Gender.MAN.toString(),
            _email = "test@example.com",
        )
        val member = Member(
            id = 1L,
            loginId = "테스트_아이디",
            password = "테스트_비밀번호",
            name = "테스트_이름",
            birthDate = LocalDate.now(),
            gender = Gender.MAN,
            email = "테스트_이메일",
        )
        val memberRole = MemberRole(role = ROLE.MEMBER, member = member)

        every { memberRepository.findByLoginId("테스트_로그인_아이디") } returns null
        every { memberRepository.save(any()) } returns member
        every { memberRoleRepository.save(any()) } returns memberRole


        When("유저 회원 가입을 진행하면") {
            val result = memberService.signUp(memberSignupDto)
            Then("정상적으로 회원 가입이 진행되어야한다.") {

                result should be("회원가입이 완료되었습니다.")
            }
        }
    }
})

//class MemberServiceTest:BehaviorSpe {
//
//
//    @BeforeEach
//    fun setUp() {
//        MockKAnnotations.init(this, relaxed = true)
//    }
//
//    @Test
//    fun testMockk() {
//        // 회원 생성
//        val member = MemberDtoResponse(
//            id = 100L,
//            loginId = "테스트 로그인 아이디",
//            name = "테스트 이름",
//            birthDate = "2024-03-04", // 고정된 날짜 사용
//            gender = Gender.MAN.toString(),
//            email = "test@example.com",
//            roles = listOf(ROLE.MEMBER)
//        )
//        val member1 = Member(
//            id = 100L,
//            loginId = "테스트 로그인 아이디",
//            password = "테스트 비밀번호",
//            name = "테스트 이름",
//            birthDate = LocalDate.now(), // 고정된 날짜 사용
//            gender = Gender.MAN,
//            email = "test@example.com",
//        )
//        every { memberRepository.findByIdOrNull(100L) } returns member1
//
//        val result = memberService.searchMyInfo(100L)
//
//        // signUp 메서드가 한 번 호출되었는지 확인
//        verify(exactly = 1) { memberRepository.findByIdOrNull(100L) }
//
//        confirmVerified(memberRepository)
//
//    }
//
////    @Test
////    fun whenGetBankAccount_thenReturnBankAccount() {
////        var member = Member(
////            id = 1,
////            loginId = "test",
////            password = "password123!",
////            name = "Test User",
////            birthDate = LocalDate.now(),
////            gender = Gender.MAN,
////            email = "test@example.com",
////        )
////        memberRepository.save(member)
////        member = memberRepository.findByLoginId("test")!!
////        //given
////        every { memberRepository.findByIdOrNull(1) } returns member;
////
////        //when
////        val result = memberRepository.getReferenceById(1);
////
////        //then
////        verify(exactly = 1) { memberRepository.findByIdOrNull(1) };
////        assertEquals(member, result)
////    }
////
////    @Test
////    fun 멤버_생성() {
////        //given
////        val memberLoginId = "test_user"
////        val member = MemberDtoRequest(
////            id = null,
////            _loginId = memberLoginId,
////            _password = "password123!",
////            _name = "Test User",
////            _birthDate = LocalDate.now().toString(),
////            _gender = Gender.MAN.toString(),
////            _email = "test@example.com",
////        )
////
////        //when
////        memberService.signUp(member)
////        val savedMember = memberRepository.findByLoginId(memberLoginId)
////
////        //then
//////        verify(exactly==1) { memberRepository.findByLoginId(memberLoginId) }
////        if (savedMember != null) {
////            Assertions.assertThat(member.id).isEqualTo(savedMember.id)
////        }
////    }
//}