package com.cuizhanming.template.kotlin.springsecurity.atp.repository

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository {

    private val refreshTokens = mutableMapOf<String, UserDetails>()

    fun save(token: String, user: UserDetails) {
        refreshTokens[token] = user
    }

    fun findUserIdByToken(token: String): UserDetails? {
        return refreshTokens[token]
    }
}