package com.example.springboot.app.controllers

import com.example.springboot.app.auth.OAuth2ResourceServerSecurityConfiguration
import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.service.PermissionService
import com.example.springboot.app.utils.PermissionRequest
import com.example.springboot.app.utils.PermissionType
import org.springframework.http.ResponseEntity
import com.example.springboot.app.utils.PermissionType.*
import org.springframework.http.HttpHeaders
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PermissionController(private val permissionService: PermissionService) {

    @PostMapping("/create")
    fun createPermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody permissionRequest: PermissionRequest,
        @RequestHeader headers: HttpHeaders
    ): ResponseEntity<PermissionEntity> {
        return try {
            val snippetId = permissionRequest.snippetId
            val userId = getUserIdFromJWT(jwt)
            val ownerPermissions = setOf(READ, WRITE, EXECUTE, SHARE)
            val permissionDTO = PermissionDTO(snippetId, userId, ownerPermissions)
            val permission = permissionService.addPermission(permissionDTO)
            println(permission)
            ResponseEntity.ok(permission)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/get")
    fun getPermissionById(
        @RequestParam snippetId: String,
        @RequestHeader headers: HttpHeaders,
        @AuthenticationPrincipal jwt: Jwt,
        ): ResponseEntity<Set<PermissionType>> {
        try {
            val userId = getUserIdFromJWT(jwt)
            val permissions = permissionService.getPermissions(snippetId, userId)
            return ResponseEntity.ok(permissions.permissions)
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.status(500).body(null)
        }
    }

    private fun getUserIdFromJWT(jwt: Jwt): String {
        val auth = OAuth2ResourceServerSecurityConfiguration(
            System.getenv("AUTH0_AUDIENCE"),
            System.getenv("AUTH_SERVER_URI")
        ).jwtDecoder()
        return auth.decode(jwt.tokenValue).subject!!
    }

}