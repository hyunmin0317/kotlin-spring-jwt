package com.hyunmin.kopring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KotlinSpringJwtApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringJwtApplication>(*args)
}
