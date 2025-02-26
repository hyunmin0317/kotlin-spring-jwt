package com.hyunmin.kopring.global.validation.annotation

import com.hyunmin.kopring.global.validation.validator.PermissionCheckValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [PermissionCheckValidator::class])
annotation class PermissionCheck(
    val message: String = "권한이 없습니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
