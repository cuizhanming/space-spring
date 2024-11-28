package com.cuizhanming.template.kotlin.springsecurity.atp.service

import com.cuizhanming.template.kotlin.springsecurity.atp.config.JwtProperties
import com.cuizhanming.template.kotlin.springsecurity.atp.controller.AuthenticationRequest
import com.cuizhanming.template.kotlin.springsecurity.atp.controller.AuthenticationResponse
import com.cuizhanming.template.kotlin.springsecurity.atp.controller.RefreshRequest
import com.cuizhanming.template.kotlin.springsecurity.atp.controller.RefreshResponse
import com.cuizhanming.template.kotlin.springsecurity.atp.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val token = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)
        return AuthenticationResponse(token, refreshToken)
    }

    fun refresh(refreshRequest: RefreshRequest): RefreshResponse {
        val refreshTokenOwner = refreshTokenRepository.findUserIdByToken(refreshRequest.refreshToken)
            ?: throw IllegalArgumentException("Invalid refresh token")

        if (!tokenService.isValid(refreshRequest.refreshToken, refreshTokenOwner)) {
            throw IllegalArgumentException("Invalid refresh token")
        }

        tokenService.extractEmail(refreshRequest.refreshToken)
            .let { email ->
                val user = userDetailsService.loadUserByUsername(email)
                return if (user.username == refreshTokenOwner.username)
                    RefreshResponse(generateAccessToken(user))
                else
                    throw IllegalArgumentException("Invalid refresh token")
            }
    }

    private fun generateRefreshToken(user: UserDetails): String {
        val refreshToken = tokenService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtProperties.refreshTokenValiditySeconds)
        )
        refreshTokenRepository.save(refreshToken, user)
        return refreshToken
    }

    private fun generateAccessToken(user: UserDetails) =
        tokenService.generateToken(
            user,
            Date(System.currentTimeMillis() + jwtProperties.accessTokenValiditySeconds),
        )

}
