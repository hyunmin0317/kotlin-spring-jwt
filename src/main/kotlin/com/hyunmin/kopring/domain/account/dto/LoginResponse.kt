package com.hyunmin.kopring.domain.account.dto

data class LoginResponse(
    val memberId: Long,
    val accessToken: String,
    val refreshToken: String
)
