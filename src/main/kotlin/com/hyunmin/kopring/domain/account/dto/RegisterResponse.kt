package com.hyunmin.kopring.domain.account.dto

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import java.time.LocalDateTime

data class RegisterResponse(
    val id: Long,
    val username: String,
    val memberRole: MemberRole,
    val createdAt: LocalDateTime,
)
