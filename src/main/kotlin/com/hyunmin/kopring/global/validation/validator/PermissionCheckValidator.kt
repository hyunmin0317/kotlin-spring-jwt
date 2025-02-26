package com.hyunmin.kopring.global.validation.validator

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.validation.annotation.PermissionCheck
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * PermissionCheck 어노테이션에 대한 유효성 검사를 수행하는 Validator 클래스
 */
@Component
class PermissionCheckValidator : ConstraintValidator<PermissionCheck, Long> {

    /**
     * 주어진 값에 대한 권한 검사를 수행
     *
     * @return 권한이 있으면 true, 없으면 false (HandlerMethodValidationException)
     */
    override fun isValid(value: Long?, context: ConstraintValidatorContext): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication == null || !checkPermission(value.toString(), authentication)) {
            addViolation(context)
            false
        } else {
            true
        }
    }

    // 인증 정보를 바탕으로 권한 확인
    private fun checkPermission(value: String, authentication: Authentication): Boolean =
        isSameMember(value, authentication.name) || isAdmin(authentication.authorities)

    // 주어진 값이 현재 인증된 멤버의 ID와 일치하는지 확인
    private fun isSameMember(value: String, memberId: String): Boolean = value == memberId

    // 주어진 권한 목록에 관리자 권한이 포함되어 있는지 확인
    private fun isAdmin(authorities: Collection<GrantedAuthority>): Boolean =
        authorities.any { it.authority == MemberRole.ROLE_ADMIN.name }

    // 유효성 검사 실패 시 위반 사항을 추가 (ErrorCode 이름)
    private fun addViolation(context: ConstraintValidatorContext) {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(ErrorCode.MEMBER_FORBIDDEN.name)
            .addConstraintViolation()
    }
}
