package com.hyunmin.kopring.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ChangePasswordRequest(
    @field:NotBlank(message = "비밀번호 입력은 필수 입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자리 이이어야 합니다.")
    val password: String
)
