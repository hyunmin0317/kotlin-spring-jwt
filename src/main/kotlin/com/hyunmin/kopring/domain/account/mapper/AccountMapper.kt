package com.hyunmin.kopring.domain.account.mapper

import com.hyunmin.kopring.domain.account.dto.LoginResponse
import com.hyunmin.kopring.domain.account.dto.RegisterRequest
import com.hyunmin.kopring.domain.account.dto.RegisterResponse
import com.hyunmin.kopring.global.common.entity.Member
import io.mcarle.konvert.api.Konvert
import io.mcarle.konvert.api.Konverter
import io.mcarle.konvert.api.Mapping

@Konverter
interface AccountMapper {

    @Konvert(
        mappings = [
            Mapping(target = "id", expression = "member.id!!"),
            Mapping(target = "memberRole", source = "role"),
        ]
    )
    fun toResponse(member: Member): RegisterResponse

    fun toResponse(memberId: Long, accessToken: String, refreshToken: String): LoginResponse =
        LoginResponse(memberId, accessToken, refreshToken)

    fun toEntity(request: RegisterRequest, encodedPw: String): Member =
        Member(username = request.username, password = encodedPw)
}
