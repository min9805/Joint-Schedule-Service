package com.example.joinu.team.service

import com.example.joinu.common.exception.InvalidInputException
import com.example.joinu.member.entity.Member
import com.example.joinu.member.repository.MemberRepository
import com.example.joinu.team.dto.*
import com.example.joinu.team.entity.MemberTeam
import com.example.joinu.team.entity.Team
import com.example.joinu.team.entity.TeamEvent
import com.example.joinu.team.repository.MemberTeamRepository
import com.example.joinu.team.repository.TeamEventRepository
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
    private val teamEventRepository: TeamEventRepository,
) {
    /**
     * 팀 조회
     */
    fun findTeamsByMemberId(memberId: Long): List<GetMemberTeamDtoResponse> {
        val memberTeams = memberTeamRepository.findByMemberId(memberId)
        return memberTeams.map { it.toGetMemberTeamDtoResponse() }
    }

    /**
     * 팀 생성
     */
    fun create(createTeamDtoRequest: CreateTeamDtoRequest, id: Long): CreateTeamDtoResponse {

        //Id 검사
        val member: Member =
            memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않는 유저입니다.")

        val team = createTeamDtoRequest.toEntity()
        val newTeam = teamRepository.save(team)

        val memberTeam =
            MemberTeam(member = member, team = newTeam, groupName = createTeamDtoRequest.name, memberName = member.name)
        memberTeamRepository.save(memberTeam)

        return newTeam.toCreateTeamDtoResponse();
    }

    /**
     * 팀에 속한 그룹원 조회
     */
    fun getMembers(teamId: Long): List<GetTeamMembersDtoResponse> {
        val memberTeams = memberTeamRepository.findByTeamId(teamId)
        return memberTeams.map { it.toGetTeamMembersDtoResponse() }
    }

    /**
     * 팀이 가진 이벤트 조회
     */
    fun getTeamEvents(teamId: Long): List<GetTeamEventsDtoResponse>? {
        val team: Team =
            teamRepository.findByIdOrNull(teamId) ?: throw InvalidInputException("팀 ID 가 존재하지 않습니다.")

        return team.teamEvent?.map { it.toGetTeamEventsDtoResponse() }
    }

    /**
     * 팀에 이벤트 생성
     */
    fun createTeamEvents(memberId: Long, teamId: Long, createTeamEventsDtoRequest: CreateTeamEventsDtoRequest): String {
        val member: Member =
            memberRepository.findByIdOrNull(memberId) ?: throw InvalidInputException(
                "id",
                "회원번호(${memberId})가 존재하지 않는 유저입니다."
            )

        val team: Team =
            teamRepository.findByIdOrNull(teamId) ?: throw InvalidInputException("팀 ID 가 존재하지 않습니다.")


        val teamEvent = createTeamEventsDtoRequest.toEntity()
        teamEvent.member = member
        teamEvent.team = team
        teamEventRepository.save(teamEvent)

        return "팀에 이벤트가 생성되었습니다.";
    }

    /**
     * 팀에 멤버 추가
     */
    fun addMember(addMemberDtoRequest: AddMemberDtoRequest): String {
        val member: Member =
            memberRepository.findByIdOrNull(addMemberDtoRequest.id) ?: throw InvalidInputException(
                "id",
                "회원번호(${addMemberDtoRequest.id})가 존재하지 않는 유저입니다."
            )

        val team: Team =
            teamRepository.findByIdOrNull(addMemberDtoRequest.groupId)
                ?: throw InvalidInputException("팀 ID 가 존재하지 않습니다.")
        
        memberTeamRepository.save(
            MemberTeam(
                groupName = team.name,
                memberName = member.name,
                member = member,
                team = team
            )
        )

        return "${team.name} 에 ${member.loginId} 멤버가 추가되었습니다."
    }
}
