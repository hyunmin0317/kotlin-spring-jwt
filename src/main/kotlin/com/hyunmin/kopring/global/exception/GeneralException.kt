package com.hyunmin.kopring.global.exception

import com.hyunmin.kopring.global.exception.code.ErrorCode

open class GeneralException(
    val errorCode: ErrorCode,
) : RuntimeException() {

    override val message: String
        get() = "[${errorCode.code}] ${errorCode.message}"
}
