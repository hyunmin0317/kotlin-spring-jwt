package com.hyunmin.kopring.global.security.handler

import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.security.exception.handler.JwtAuthenticationExceptionHandler
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

/**
 * 접근 권한이 없는 요청에 대한 오류 응답을 처리하는 핸들러 클래스
 * 사용자가 요청한 자원에 대한 접근 권한이 없는 경우
 */
class JwtAccessDeniedHandler : AccessDeniedHandler {

    /**
     * 접근 거부 시 호출되는 메서드
     */
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: AccessDeniedException
    ) {
        JwtAuthenticationExceptionHandler.handleException(response, ex, ErrorCode.FORBIDDEN_EXCEPTION)
    }
}
