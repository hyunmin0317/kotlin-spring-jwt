package com.hyunmin.kopring.global.security.provider

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.security.config.JwtProperties
import com.hyunmin.kopring.global.security.exception.JwtAuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
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

    /**
     * 요청 헤더에서 JWT 토큰 추출
     */
    fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)

    /**
     * JWT 토큰 유효성 검증
     */
    fun validateToken(token: String?): Boolean {
        if (!StringUtils.hasText(token)) return false
        try {
            getClaims(token)
            return true
        } catch (e: ExpiredJwtException) {
            throw JwtAuthenticationException(ErrorCode.EXPIRED_JWT_EXCEPTION)
        } catch (e: JwtException) {
            throw JwtAuthenticationException(ErrorCode.INVALID_TOKEN_EXCEPTION)
        } catch (e: IllegalArgumentException) {
            throw JwtAuthenticationException(ErrorCode.INVALID_TOKEN_EXCEPTION)
        }
    }

    /**
     * JWT 토큰을 기반으로 Authentication 객체 생성
     */
    fun getAuthentication(token: String?): Authentication {
        val claims = getClaims(token)
        val memberRole = claims["memberRole"].toString()
        val authorities = listOf(SimpleGrantedAuthority(memberRole))
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    // 액세스 토큰의 만료 시간 계산
    private fun expirationDate(isRefresh: Boolean): Date =
        Date(Date().time + jwtProperties.getExpirationTime(isRefresh) * 1000)

    // JWT 토큰에서 클레임을 추출
    private fun getClaims(token: String?): Claims =
        Jwts.parser().verifyWith(jwtProperties.getSecretKey()).build().parseSignedClaims(token).payload
}
