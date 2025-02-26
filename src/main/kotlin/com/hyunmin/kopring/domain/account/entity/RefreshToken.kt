package com.hyunmin.kopring.domain.account.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash
class RefreshToken(
    @Id
    val token: String,

    val memberId: Long,

    @TimeToLive
    val expirationTime: Long
)
