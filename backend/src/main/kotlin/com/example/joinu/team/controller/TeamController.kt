package com.example.joinu.team.controller

import com.example.joinu.common.dto.BaseResponse
import com.example.joinu.common.dto.CustomUser
import com.example.joinu.team.dto.CreateTeamDtoRequest
import com.example.joinu.team.dto.GetTeamsDtoResponse
import com.example.joinu.team.entity.Team
import com.example.joinu.team.service.TeamService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/")
    fun getTeamsByMemberId(): BaseResponse<List<GetTeamsDtoResponse>> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val result = teamService.findTeamsByMemberId(userId)
        return BaseResponse(data = result)
    }

    @PostMapping("/create")
    fun createTeam(@RequestBody createTeamDtoRequest: CreateTeamDtoRequest): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg = teamService.create(createTeamDtoRequest, userId)
        return BaseResponse(message = resultMsg)
    }
}