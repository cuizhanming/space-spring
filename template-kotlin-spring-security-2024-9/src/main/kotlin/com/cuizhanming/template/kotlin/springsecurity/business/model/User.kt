package com.cuizhanming.template.kotlin.springsecurity.business.model

import java.util.UUID

data class User(
    var id: UUID,
    val email: String,
    val password: String,
    val role: Role
) {
}

enum class Role {
    USER, ADMIN
}
