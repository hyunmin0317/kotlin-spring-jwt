package com.hyunmin.kopring.domain.account.controller

import com.hyunmin.kopring.domain.account.dto.LoginRequest
import com.hyunmin.kopring.domain.account.dto.LoginResponse
import com.hyunmin.kopring.domain.account.dto.RegisterRequest
import com.hyunmin.kopring.domain.account.dto.RegisterResponse
import com.hyunmin.kopring.domain.account.service.AccountService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        val response = accountService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody requestDto: LoginRequest): ResponseEntity<LoginResponse> {
        val responseDto: LoginResponse = accountService.login(requestDto)
        return ResponseEntity.ok(responseDto)
    }
}
