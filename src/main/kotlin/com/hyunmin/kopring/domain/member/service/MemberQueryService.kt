package com.hyunmin.kopring.domain.member.service

import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.global.common.repository.MemberRepository
import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberQueryService(
    private val memberRepository: MemberRepository
) {

    fun findAll(pageable: Pageable): Page<MemberInfoResponse> {
        val memberPage = memberRepository.findAll(pageable)
        return memberPage.map { MemberInfoResponse(it.id!!, it.username, it.role, it.createdAt, it.updatedAt) }
    }

    fun findById(id: Long): MemberInfoResponse {
        val member = memberRepository.findById(id)
            .orElseThrow { GeneralException(ErrorCode.MEMBER_NOT_FOUND) }
        return MemberInfoResponse(member.id!!, member.username, member.role, member.createdAt, member.updatedAt)
    }
}
