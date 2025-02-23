package com.hyunmin.kopring.global.security.provider

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import com.hyunmin.kopring.global.security.config.JwtProperties
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

/**
 * JWT 토큰을 생성하고 검증하는 클래스
 */
@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {

    /**
     * JWT access 토큰 생성
     */
    fun createAccessToken(memberId: Long, memberRole: MemberRole, isRefresh: Boolean): String =
        Jwts.builder()
            .subject(memberId.toString())
            .claim("memberRole", memberRole.name)
            .signWith(jwtProperties.getSecretKey())
            .expiration(expirationDate(isRefresh))
            .compact()

    // 액세스 토큰의 만료 시간 계산
    private fun expirationDate(isRefresh: Boolean): Date =
        Date(Date().time + jwtProperties.getExpirationTime(isRefresh) * 1000)
}
