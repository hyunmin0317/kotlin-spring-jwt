package com.hyunmin.kopring.global.exception.handler

import com.hyunmin.kopring.global.common.dto.ErrorResponse
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GeneralExceptionHandler {

    private val log = LoggerFactory.getLogger(GeneralExceptionHandler::class.java)

    // 사용자 정의 예외(GeneralException) 처리 메서드
    @ExceptionHandler(GeneralException::class)
    fun handleGeneralException(ex: GeneralException): ResponseEntity<ErrorResponse<Void>> {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)
        return ErrorResponse.handle(ex.errorCode)
    }

    // 요청 파라미터 검증 실패(MethodArgumentNotValidException) 처리 메서드
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse<Map<String, String>>> {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)
        return ErrorResponse.handle(ErrorCode.VALIDATION_FAILED, ex.fieldErrors)
    }

    // 지원되지 않는 HTTP 메서드 요청(HttpRequestMethodNotSupportedException) 처리 메서드
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse<Void>> {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)
        return ErrorResponse.handle(ErrorCode.METHOD_NOT_ALLOWED)
    }

    // 기타 모든 예외(Exception) 처리 메서드
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse<Void>> {
        log.error("[ERROR] {} : {}", ex.javaClass, ex.message, ex)
        return ErrorResponse.handle(ErrorCode.INTERNAL_SERVER_ERROR)
    }
}
