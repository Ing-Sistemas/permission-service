package com.example.springboot.app.dto

import com.example.springboot.app.utils.PermissionType

class PermissionDTO(val snippetId: Long, val userId:String, val permissions: Set<PermissionType>)