package com.hyunmin.kopring.domain.member.controller

import com.hyunmin.kopring.domain.member.dto.ChangePasswordRequest
import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.domain.member.service.MemberCommandService
import com.hyunmin.kopring.domain.member.service.MemberQueryService
import com.hyunmin.kopring.global.security.annotation.AuthMember
import com.hyunmin.kopring.global.validation.annotation.PermissionCheck
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberQueryService: MemberQueryService,
    private val memberCommandService: MemberCommandService
) {

    @GetMapping
    fun findAll(@ParameterObject pageable: Pageable): ResponseEntity<Page<MemberInfoResponse>> {
        val responsePage = memberQueryService.findAll(pageable)
        return ResponseEntity.ok(responsePage)
    }

    @GetMapping("/{id}")
    fun findById(@PermissionCheck @PathVariable id: Long): ResponseEntity<MemberInfoResponse> {
        val response = memberQueryService.findById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/me")
    fun getMemberInfo(@AuthMember memberId: Long): ResponseEntity<MemberInfoResponse> {
        val response = memberQueryService.findById(memberId)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/me")
    fun changePassword(
        @AuthMember memberId: Long,
        @Valid @RequestBody request: ChangePasswordRequest
    ): ResponseEntity<MemberInfoResponse> {
        val response = memberCommandService.changePassword(memberId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/me")
    fun deleteMember(@AuthMember memberId: Long): ResponseEntity<Void> {
        memberCommandService.deleteMember(memberId)
        return ResponseEntity.noContent().build()
    }
}
