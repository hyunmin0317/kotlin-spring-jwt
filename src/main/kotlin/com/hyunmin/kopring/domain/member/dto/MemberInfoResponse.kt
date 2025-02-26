package com.hyunmin.kopring.domain.member.dto

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import java.time.LocalDateTime

data class MemberInfoResponse(
    val id: Long,
    val username: String,
    val memberRole: MemberRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
