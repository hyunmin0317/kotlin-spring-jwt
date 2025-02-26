package com.hyunmin.kopring.domain.member.service

import com.hyunmin.kopring.domain.member.dto.ChangePasswordRequest
import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.global.common.repository.MemberRepository
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberCommandService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun changePassword(id: Long, request: ChangePasswordRequest): MemberInfoResponse {
        val member = memberRepository.findById(id)
            .orElseThrow { GeneralException(ErrorCode.MEMBER_NOT_FOUND) }
        val newEncodedPw = passwordEncoder.encode(request.password)
        member.changePassword(newEncodedPw)
        return MemberInfoResponse(member.id!!, member.username, member.role, member.createdAt, member.updatedAt)
    }

    fun deleteMember(id: Long) {
        val member = memberRepository.findById(id)
            .orElseThrow { GeneralException(ErrorCode.MEMBER_NOT_FOUND) }
        memberRepository.delete(member)
    }
}
