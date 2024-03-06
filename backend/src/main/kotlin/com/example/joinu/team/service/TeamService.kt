package com.example.joinu.team.service

import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.entity.Event
import com.example.joinu.event.entity.MemberEvent
import com.example.joinu.member.entity.Member
import com.example.joinu.member.repository.MemberRepository
import com.example.joinu.team.dto.CreateTeamDtoRequest
import com.example.joinu.team.dto.GetTeamsDtoResponse
import com.example.joinu.team.entity.MemberTeam
import com.example.joinu.team.entity.Team
import com.example.joinu.team.repository.MemberTeamRepository
import com.example.joinu.team.repository.TeamRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TeamService(
    private val memberTeamRepository: MemberTeamRepository,
    private val memberRepository: MemberRepository,
    private val teamRepository: TeamRepository,
) {
    /**
     * 팀 조회
     */
    fun findTeamsByMemberId(memberId: Long): List<GetTeamsDtoResponse> {
        val memberTeams = memberTeamRepository.findByMemberId(memberId)
        return memberTeams.map { it.team.toDto() }
    }

    /**
     * 팀 생성
     */
    fun create(createTeamDtoRequest: CreateTeamDtoRequest, id: Long): String {

        //Id 검사
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")

        val team = createTeamDtoRequest.toEntity()
        val newTeam = teamRepository.save(team)

        val memberTeam = MemberTeam(member = member, team = newTeam)
        memberTeamRepository.save(memberTeam)

        return "Team 생성이 완료되었습니다.";
    }

}