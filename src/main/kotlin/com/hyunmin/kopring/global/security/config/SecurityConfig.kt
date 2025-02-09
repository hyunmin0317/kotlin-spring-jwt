package com.hyunmin.kopring.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * Spring Security 설정을 구성하는 클래스
 * HTTP 보안 설정, 인증 필터 추가 등 보안 관련 설정을 정의
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    /**
     * BCrypt 암호화 방식으로 PasswordEncoder 빈을 생성
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    /**
     * Spring Security의 필터 체인 설정 구성
     */
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // CSRF 보호 비활성화
        http.csrf { it.disable() }

        // HTTP 응답 헤더 설정
        http.headers { it.frameOptions { frameOptions -> frameOptions.sameOrigin() } }

        // 경로별 인가 작업
        http.authorizeHttpRequests {
            // H2 콘솔과 Swagger UI 및 API 문서에 대한 접근 허용
            it.requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            // API 계정 관련 요청에 대한 접근 허용
            it.requestMatchers("/api/v1/accounts/**", "/actuator/info").permitAll()
            // 나머지 모든 요청은 인증 필요
            it.anyRequest().authenticated()
        }

        return http.build()
    }
}
