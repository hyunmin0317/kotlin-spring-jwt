package com.hyunmin.kopring.domain.member.mapper

import com.hyunmin.kopring.domain.member.dto.MemberInfoResponse
import com.hyunmin.kopring.global.common.entity.Member
import io.mcarle.konvert.api.Konvert
import io.mcarle.konvert.api.Konverter
import io.mcarle.konvert.api.Mapping
import org.springframework.data.domain.Page

@Konverter
interface MemberMapper {

    @Konvert(
        mappings = [
            Mapping(target = "id", expression = "member.id!!"),
            Mapping(target = "memberRole", source = "role"),
        ]
    )
    fun toResponse(member: Member): MemberInfoResponse

    fun toResponse(members: Page<Member>): Page<MemberInfoResponse> =
        members.map { toResponse(it) }
}
