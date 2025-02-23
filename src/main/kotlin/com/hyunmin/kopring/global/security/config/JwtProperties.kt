package com.hyunmin.kopring.global.security.config

import io.jsonwebtoken.Jwts
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = "",
    var token: TokenProperties = TokenProperties()
) {

    data class TokenProperties(
        var accessExpirationTime: Long = 0,
        var refreshExpirationTime: Long = 0
    )

    // 비밀 키 생성
    fun getSecretKey(): SecretKey =
        SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().algorithm)

    // JWT 토큰의 만료 시간을 반환
    fun getExpirationTime(isRefresh: Boolean): Long =
        if (isRefresh) token.refreshExpirationTime else token.accessExpirationTime
}
