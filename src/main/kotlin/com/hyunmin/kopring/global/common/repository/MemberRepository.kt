package com.hyunmin.kopring.global.common.repository

import com.hyunmin.kopring.global.common.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>
