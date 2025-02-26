package com.hyunmin.kopring.global.exception.handler

import com.hyunmin.kopring.global.common.dto.ErrorResponse
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException

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

    // 데이터 무결성 위반(DataIntegrityViolationException) 처리 메서드
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse<Void>> {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)
        return ErrorResponse.handle(ErrorCode.VALIDATION_FAILED)
    }

    // 컨트롤러 메서드 파라미터의 유효성 검증 실패(HandlerMethodValidationException) 처리 메서드 - @PermissionCheckValidator
    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(ex: HandlerMethodValidationException): ResponseEntity<ErrorResponse<Void>> {
        log.warn("[WARNING] {} : {}", ex.javaClass, ex.message)
        return ErrorResponse.handle(extractErrorCode(ex))
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

    // HandlerMethodValidationException 에서 ErrorCode 를 추출하는 메서드
    private fun extractErrorCode(ex: HandlerMethodValidationException): ErrorCode {
        return ex.parameterValidationResults
            .flatMap { result -> result.resolvableErrors }
            .map { it.defaultMessage }
            .filterNotNull()
            .firstOrNull()
            ?.let { ErrorCode.valueOf(it) }
            ?: throw GeneralException(ErrorCode.BAD_REQUEST)
    }
}
