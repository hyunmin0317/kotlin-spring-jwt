package com.hyunmin.kopring.domain.account.repository

import com.hyunmin.kopring.domain.account.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String>
