package com.cuizhanming.template.kotlin.springsecurity.atp.controller

import com.cuizhanming.template.kotlin.springsecurity.atp.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController (
    private val authenticationService: AuthenticationService
) {
    @PostMapping
    fun authenticate(@RequestBody authenticationRequest: AuthenticationRequest): AuthenticationResponse =
        authenticationService.authenticate(authenticationRequest)

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshRequest: RefreshRequest): RefreshResponse =
        authenticationService.refresh(refreshRequest)
}

data class RefreshResponse (
    val accessToken: String
)

data class RefreshRequest (
    val refreshToken: String
)

data class AuthenticationResponse (
    val accessToken: String,
    val refreshToken: String
)

data class AuthenticationRequest (
    val email: String,
    val password: String
)
