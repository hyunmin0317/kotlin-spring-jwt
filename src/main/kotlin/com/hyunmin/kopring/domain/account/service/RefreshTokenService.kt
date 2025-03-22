package com.hyunmin.kopring.domain.account.service

import com.hyunmin.kopring.domain.account.entity.RefreshToken
import com.hyunmin.kopring.domain.account.repository.RefreshTokenRepository
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.security.config.JwtProperties
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JwtProperties,
) {

    fun findRefreshToken(token: String): RefreshToken =
        refreshTokenRepository.findById(token)
            .orElseThrow { GeneralException(ErrorCode.INVALID_REFRESH_TOKEN) }

    @Transactional
    fun saveRefreshToken(memberId: Long, token: String) {
        val expirationTime = jwtProperties.getExpirationTime(true)
        val refreshToken = RefreshToken(token, memberId, expirationTime)
        refreshTokenRepository.save(refreshToken)
    }

    @Transactional
    fun deleteRefreshToken(token: String) {
        val target = refreshTokenRepository.findById(token)
        target.ifPresent(refreshTokenRepository::delete)
    }
}
