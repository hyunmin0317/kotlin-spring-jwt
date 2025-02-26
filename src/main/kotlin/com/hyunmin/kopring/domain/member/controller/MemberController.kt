package com.hyunmin.kopring.domain.member.controller

import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.domain.member.service.MemberQueryService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberQueryService: MemberQueryService
) {

    @GetMapping
    fun findAll(@ParameterObject pageable: Pageable): ResponseEntity<Page<MemberInfoResponse>> {
        val responsePage = memberQueryService.findAll(pageable)
        return ResponseEntity.ok(responsePage)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<MemberInfoResponse> {
        val response = memberQueryService.findById(id)
        return ResponseEntity.ok(response)
    }
}
