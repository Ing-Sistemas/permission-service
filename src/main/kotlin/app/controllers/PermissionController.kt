package com.example.springboot.app.controllers

import com.example.springboot.app.auth.OAuth2ResourceServerSecurityConfiguration
import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.service.PermissionService
import com.example.springboot.app.utils.PermissionRequest
import com.example.springboot.app.utils.PermissionType
import org.springframework.http.ResponseEntity
import com.example.springboot.app.utils.PermissionType.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PermissionController(private val permissionService: PermissionService) {

    @PostMapping("/create")
    fun createPermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody permissionRequest: PermissionRequest
    ): ResponseEntity<PermissionEntity> {
        try {
            val snippetId = permissionRequest.snippetId
            val auth = OAuth2ResourceServerSecurityConfiguration(
                System.getenv("AUTH0_AUDIENCE"),
                System.getenv("AUTH_SERVER_URI")
            ).jwtDecoder()
            val userId = auth.decode(jwt.tokenValue).subject!!
            val ownerPermissions = setOf(READ, WRITE, EXECUTE, SHARE)
            val permissionDTO = PermissionDTO(snippetId, userId, ownerPermissions)
            val permission = permissionService.addPermission(permissionDTO)
            println(permission)
            return ResponseEntity.ok(permission)
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/get")
    fun getPermissionById(
        @RequestParam userId: String,
        @RequestParam snippetId: String
    ): ResponseEntity<Set<PermissionType>> {
        try {
            val permissions = permissionService.getPermissions(snippetId, userId)
            return ResponseEntity.ok(permissions.permissions)
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.status(500).body(null)
        }
    }

}