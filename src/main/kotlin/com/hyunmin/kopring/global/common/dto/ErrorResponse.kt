package com.hyunmin.kopring.global.common.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError

data class ErrorResponse<T>(
    val code: String,
    val message: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val detail: T? = null
) {

    fun toJsonString(): String = objectMapper.writeValueAsString(this)

    companion object {
        private val objectMapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

        fun handle(errorCode: ErrorCode): ResponseEntity<ErrorResponse<Void>> =
            ResponseEntity
                .status(HttpStatus.valueOf(errorCode.value))
                .body(from(errorCode))

        fun handle(
            errorCode: ErrorCode,
            fieldErrors: List<FieldError>
        ): ResponseEntity<ErrorResponse<Map<String, String>>> =
            ResponseEntity
                .status(HttpStatus.valueOf(errorCode.value))
                .body(of(errorCode, fieldErrors))

        fun <T> from(errorCode: ErrorCode): ErrorResponse<T> =
            ErrorResponse(errorCode.code, errorCode.message, null)

        private fun of(errorCode: ErrorCode, fieldErrors: List<FieldError>): ErrorResponse<Map<String, String>> =
            ErrorResponse(errorCode.code, errorCode.message, convertErrors(fieldErrors))

        private fun convertErrors(fieldErrors: List<FieldError>): Map<String, String> =
            fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
    }
}
