package com.example.springboot.app.dto

import com.example.springboot.app.utils.PermissionType

data class PermissionDTO(
    val snippetId: String,
    val userId: String,
    val permissions: Set<PermissionType>,
)
