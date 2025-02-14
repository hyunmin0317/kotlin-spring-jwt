package com.hyunmin.kopring.domain.account.service

import com.hyunmin.kopring.domain.account.dto.RegisterRequest
import com.hyunmin.kopring.domain.account.dto.RegisterResponse
import com.hyunmin.kopring.global.common.entity.Member
import com.hyunmin.kopring.global.common.repository.MemberRepository
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    val memberRepository: MemberRepository,
    val passwordEncoder: PasswordEncoder
) {

    fun register(request: RegisterRequest): RegisterResponse {
        validateUsername(request.username)
        val encodedPw = passwordEncoder.encode(request.password)
        val member = Member(username = request.username, password = encodedPw)
        val saved = memberRepository.save(member)
        return RegisterResponse(saved.id!!, saved.username, saved.role, saved.createdAt)
    }

    private fun validateUsername(username: String) {
        if (memberRepository.existsByUsername(username)) {
            throw GeneralException(ErrorCode.ACCOUNT_CONFLICT)
        }
    }
}
