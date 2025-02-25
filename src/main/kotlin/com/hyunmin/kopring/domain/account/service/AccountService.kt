package com.hyunmin.kopring.domain.account.service

import com.hyunmin.kopring.domain.account.dto.LoginRequest
import com.hyunmin.kopring.domain.account.dto.LoginResponse
import com.hyunmin.kopring.domain.account.dto.RegisterRequest
import com.hyunmin.kopring.domain.account.dto.RegisterResponse
import com.hyunmin.kopring.global.common.entity.Member
import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import com.hyunmin.kopring.global.common.repository.MemberRepository
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import com.hyunmin.kopring.global.security.provider.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun register(request: RegisterRequest): RegisterResponse {
        validateUsername(request.username)
        val encodedPw = passwordEncoder.encode(request.password)
        val member = Member(username = request.username, password = encodedPw)
        val saved = memberRepository.save(member)
        return RegisterResponse(saved.id!!, saved.username, saved.role, saved.createdAt)
    }

    fun login(requestDto: LoginRequest): LoginResponse {
        val member = memberRepository.findByUsername(requestDto.username)
            .orElseThrow { GeneralException(ErrorCode.ACCOUNT_NOT_FOUND) }
        checkPassword(requestDto.password, member.password)
        return generateToken(member.id!!, member.role)
    }

    private fun generateToken(memberId: Long, memberRole: MemberRole): LoginResponse {
        val accessToken = jwtTokenProvider.createAccessToken(memberId, memberRole, false)
        val refreshToken = jwtTokenProvider.createAccessToken(memberId, memberRole, true)
        return LoginResponse(memberId, accessToken, refreshToken)
    }

    private fun validateUsername(username: String) {
        if (memberRepository.existsByUsername(username)) {
            throw GeneralException(ErrorCode.ACCOUNT_CONFLICT)
        }
    }

    private fun checkPassword(requestPassword: String, memberPassword: String) {
        if (!passwordEncoder.matches(requestPassword, memberPassword)) {
            throw GeneralException(ErrorCode.PASSWORD_NOT_MATCH)
        }
    }
}
