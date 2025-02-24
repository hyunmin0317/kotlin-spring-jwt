package com.hyunmin.kopring.global.security.handler

import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.security.exception.handler.JwtAuthenticationExceptionHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

/**
 * JWT 인증 실패 시 오류 응답을 보내는 EntryPoint 클래스
 * JWT 인증 시 인증 정보가 없거나 잘못된 경우
 */
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    /**
     * 인증 실패 시 호출되는 메서드
     */
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: AuthenticationException
    ) {
        JwtAuthenticationExceptionHandler.handleException(response, ex, ErrorCode.UNAUTHORIZED_EXCEPTION)
    }
}
