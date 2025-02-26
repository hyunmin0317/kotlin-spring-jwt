package com.hyunmin.kopring.global.security.filter

import com.hyunmin.kopring.global.security.provider.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 요청에서 JWT 토큰 추출
        val jwt = jwtTokenProvider.resolveToken(request)

        // JWT 토큰이 유효한지 검증 후 인증 정보를 가져와서 설정
        val authentication =
            if (jwtTokenProvider.validateToken(jwt, false)) jwtTokenProvider.getAuthentication(jwt) else null

        // 인증 정보를 SecurityContext에 설정
        SecurityContextHolder.getContext().authentication = authentication

        // 필터 체인을 통해 다음 필터로 요청을 전달
        filterChain.doFilter(request, response)
    }
}
