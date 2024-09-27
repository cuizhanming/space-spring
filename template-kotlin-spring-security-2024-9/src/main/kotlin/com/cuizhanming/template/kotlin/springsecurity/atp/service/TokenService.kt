package com.cuizhanming.template.kotlin.springsecurity.atp.service

import com.cuizhanming.template.kotlin.springsecurity.atp.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService (
    jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.key!!.toByteArray())

    fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String = Jwts.builder()
        .claims()
        .subject(userDetails.username)
        .issuedAt(Date())
        .expiration(expirationDate)
        .add(additionalClaims)
        .and()
        .signWith(secretKey)
        .compact()

    fun extractEmail(token: String): String? = getAllClaims(token).subject

    fun isTokenExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return email == userDetails.username && !isTokenExpired(token)
    }

    private fun getAllClaims(token: String): Claims = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .payload


}