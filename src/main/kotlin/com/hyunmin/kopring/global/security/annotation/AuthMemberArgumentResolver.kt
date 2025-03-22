package com.hyunmin.kopring.global.security.annotation

import com.hyunmin.kopring.global.security.provider.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

/**
 * 컨트롤러 메서드의 파라미터가 @AuthMember Member 타입일 때 해당 파라미터를 처리하도록 지정하는 클래스
 */
@Component
class AuthMemberArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandlerMethodArgumentResolver {

    /**
     * 파라미터 타입 확인 (@AuthMember, Member)
     */
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAuthMemberAnnotation = parameter.hasParameterAnnotation(AuthMember::class.java)
        val isMemberType = Long::class.java.isAssignableFrom(parameter.parameterType)
        return hasAuthMemberAnnotation && isMemberType
    }

    /**
     * 해당 컨트롤러 메서드의 파라미터 처리
     */
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long? {
        val request = webRequest.nativeRequest as HttpServletRequest
        val jwt = jwtTokenProvider.resolveToken(request)
        return if (jwtTokenProvider.validateToken(jwt, false)) {
            jwtTokenProvider.getAuthentication(jwt).name.toLong()
        } else {
            null
        }
    }
}
