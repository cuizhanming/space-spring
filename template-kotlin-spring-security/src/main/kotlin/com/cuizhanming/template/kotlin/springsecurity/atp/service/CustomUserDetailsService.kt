package com.cuizhanming.template.kotlin.springsecurity.atp.service

import com.cuizhanming.template.kotlin.springsecurity.business.rest.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

typealias AppUser = com.cuizhanming.template.kotlin.springsecurity.business.rest.User

class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User not found")

    private fun AppUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(email)
            .password(password)
            .roles(role.name)
            .build()
}