package com.cuizhanming.template.kotlin.springsecurity.atp.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    var key: String? = null
    var accessTokenValiditySeconds: Long = 0
    var refreshTokenValiditySeconds: Long = 0
}