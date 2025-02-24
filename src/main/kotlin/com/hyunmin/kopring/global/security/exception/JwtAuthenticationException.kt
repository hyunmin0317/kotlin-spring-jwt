package com.hyunmin.kopring.global.security.exception

import com.hyunmin.kopring.global.exception.GeneralException
import com.hyunmin.kopring.global.exception.code.ErrorCode

class JwtAuthenticationException(errorCode: ErrorCode) : GeneralException(errorCode)
