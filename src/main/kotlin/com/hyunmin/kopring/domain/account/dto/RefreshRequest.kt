package com.hyunmin.kopring.domain.account.dto

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "리프레시 토큰 입력은 필수 입니다.")
    val refreshToken: String
)
