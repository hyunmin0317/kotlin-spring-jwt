package com.hyunmin.kopring.global.security.exception.handler

import com.hyunmin.kopring.global.common.dto.ErrorResponse
import com.hyunmin.kopring.global.exception.code.ErrorCode
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType

/**
 * JWT 인증·인가 관련 예외를 처리하는 핸들러 클래스
 */
object JwtAuthenticationExceptionHandler {

    private val log = LoggerFactory.getLogger(JwtAuthenticationExceptionHandler::class.java)

    // JWT 인증·인가 관련 예외 발생 시 오류 응답 설정
    fun handleException(response: HttpServletResponse, ex: RuntimeException, errorCode: ErrorCode) {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)

        // 응답의 Content-Type, 문자 인코딩, 상태 코드 설정
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = errorCode.value

        // ErrorResponse 객체를 JSON 문자열로 변환하여 응답 본문에 작성
        val errorResponse = ErrorResponse.from<Any>(errorCode)
        response.writer.write(errorResponse.toJsonString())
    }
}
