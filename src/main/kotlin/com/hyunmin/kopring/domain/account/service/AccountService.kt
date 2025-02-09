package com.hyunmin.kopring.domain.account.service

import com.hyunmin.kopring.domain.account.dto.RegisterRequest
import com.hyunmin.kopring.domain.account.dto.RegisterResponse
import com.hyunmin.kopring.global.common.entity.Member
import com.hyunmin.kopring.global.common.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(
    val memberRepository: MemberRepository,
    val passwordEncoder: PasswordEncoder
) {

    fun register(request: RegisterRequest): RegisterResponse {
        val encodedPw = passwordEncoder.encode(request.password)
        val member = Member(username = request.username, password = encodedPw)
        val saved = memberRepository.save(member)
        return RegisterResponse(saved.id!!, saved.username, saved.role, saved.createdAt)
    }
}
