package com.example.springboot.app.utils

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt

data class PermissionRequest(
    val snippetId: String,
)