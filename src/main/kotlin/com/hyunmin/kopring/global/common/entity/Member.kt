package com.hyunmin.kopring.global.common.entity

import com.hyunmin.kopring.global.common.entity.enums.MemberRole
import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    var role: MemberRole = MemberRole.ROLE_USER,
) : BaseEntity() {

    fun changePassword(password: String) {
        this.password = password
    }
}
