package com.hyunmin.kopring.global.config

import com.hyunmin.kopring.global.security.annotation.AuthMemberArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Spring MVC 설정을 위한 클래스
 * WebMvcConfigurer 인터페이스를 구현하여 커스텀 설정을 추가
 */
@Configuration
class WebConfig(
    private val authMemberArgumentResolver: AuthMemberArgumentResolver,
) : WebMvcConfigurer {

    // 커스텀 Argument Resolver 추가
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authMemberArgumentResolver)
    }
}
