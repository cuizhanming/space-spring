package com.cuizhanming.template.kotlin.springsecurity.atp.config

import com.cuizhanming.template.kotlin.springsecurity.atp.service.CustomUserDetailsService
import com.cuizhanming.template.kotlin.springsecurity.business.rest.UserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class AuthConfiguration {

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService = CustomUserDetailsService(userRepository)

    @Bean
    fun authenticationProvider(userDetailsService: UserDetailsService, encoder: PasswordEncoder): DaoAuthenticationProvider =
        DaoAuthenticationProvider().apply {
            setPasswordEncoder(encoder)
            setUserDetailsService(userDetailsService)
        }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}