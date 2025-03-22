package com.hyunmin.kopring.domain.member.service

import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.domain.member.mapper.MemberMapperImpl
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
    private val memberRepository: MemberRepository,
) {

    fun findAll(pageable: Pageable): Page<MemberInfoResponse> {
        val members = memberRepository.findAll(pageable)
        return MemberMapperImpl.toResponse(members)
    }

    fun findById(id: Long): MemberInfoResponse {
        val member = memberRepository.findById(id)
            .orElseThrow { GeneralException(ErrorCode.MEMBER_NOT_FOUND) }
        return MemberMapperImpl.toResponse(member)
    }
}
