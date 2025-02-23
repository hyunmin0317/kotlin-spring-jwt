package com.hyunmin.kopring.global.common.repository

import com.hyunmin.kopring.global.common.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<Member>
}
