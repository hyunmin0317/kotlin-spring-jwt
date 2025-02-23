package com.hyunmin.kopring.domain.account.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "아이디 입력은 필수 입니다.")
    val username: String,

    @field:NotBlank(message = "비밀번호 입력은 필수 입니다.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자리 이이어야 합니다.")
    val password: String
)
