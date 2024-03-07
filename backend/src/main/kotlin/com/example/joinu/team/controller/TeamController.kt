package com.example.joinu.team.controller

import com.example.joinu.common.dto.BaseResponse
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.event.dto.EventDto
import com.example.joinu.event.entity.Event
import com.example.joinu.team.dto.*
import com.example.joinu.team.entity.Team
import com.example.joinu.team.entity.TeamEvent
import com.example.joinu.team.service.TeamService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/team")
class TeamController(
    private val teamService: TeamService
) {

    /**
     * 멤버가 가진 팀 조회
     */
    @GetMapping("/")
    fun getTeamsByMemberId(): BaseResponse<List<GetMemberTeamDtoResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = teamService.findTeamsByMemberId(userId)
        return BaseResponse(data = result)
    }

    /**
     * 멤버 이름으로 팀 생성
     */
    @PostMapping("/create")
    fun createTeam(@RequestBody createTeamDtoRequest: CreateTeamDtoRequest): BaseResponse<CreateTeamDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = teamService.create(createTeamDtoRequest, userId)
        return BaseResponse(data = result)
    }

    /**
     * 팀에 속한 멤버들 조회
     */
    @GetMapping("/members/{teamId}")
    fun getMembersByTeamId(@PathVariable teamId: Long): BaseResponse<List<GetTeamMembersDtoResponse>> {
        val result = teamService.getMembers(teamId)
        return BaseResponse(data = result)
    }

    /**
     * 팀에 속한 이벤트 조회
     */
    @GetMapping("/events/{teamId}")
    fun getEventsByTeamId(@PathVariable teamId: Long): BaseResponse<List<GetTeamEventsDtoResponse>> {
        val result = teamService.getTeamEvents(teamId)
        return BaseResponse(data = result)
    }

    /**
     * 팀에 자신의 이름으로 이벤트 생성
     */
    @PostMapping("/events/{teamId}/create")
    fun createEvent(
        @PathVariable teamId: Long,
        @RequestBody createTeamEventsDtoRequest: CreateTeamEventsDtoRequest,
    ): BaseResponse<Event> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = teamService.createTeamEvents(userId, teamId, createTeamEventsDtoRequest)
        return BaseResponse(message = result)
    }
}