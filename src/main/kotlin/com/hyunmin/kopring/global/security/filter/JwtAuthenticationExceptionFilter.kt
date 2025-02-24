package com.hyunmin.kopring.global.security.filter

import com.hyunmin.kopring.global.security.exception.JwtAuthenticationException
import com.hyunmin.kopring.global.security.exception.handler.JwtAuthenticationExceptionHandler
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

/**
 * JWT 인증 예외를 핸들링하는 필터 클래스
 */
class JwtAuthenticationExceptionFilter : OncePerRequestFilter() {

    /**
     * 요청을 필터링하고, JWT 인증 예외가 발생하면 오류 응답 설정
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // 필터 체인 계속 실행
            filterChain.doFilter(request, response)
        } catch (ex: JwtAuthenticationException) {
            // 예외 발생 시 오류 응답 설정
            JwtAuthenticationExceptionHandler.handleException(response, ex, ex.errorCode)
        }
    }
}
