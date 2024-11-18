package com.example.springboot.app.controllers

import com.example.springboot.app.auth.OAuth2ResourceServerSecurityConfiguration
import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.service.PermissionService
import com.example.springboot.app.utils.PermissionRequest
import com.example.springboot.app.utils.PermissionType
import org.springframework.http.ResponseEntity
import com.example.springboot.app.utils.PermissionType.*
import com.example.springboot.app.utils.ShareRequest
import com.example.springboot.app.utils.SnippetsGroup
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PermissionController(private val permissionService: PermissionService) {

    @PostMapping("/create")
    fun createPermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody permissionRequest: PermissionRequest
    ): ResponseEntity<PermissionEntity> {
        return try {
            val snippetId = permissionRequest.snippetId
            val userId = getUserIdFromJWT(jwt)
            val ownerPermissions = setOf(READ, WRITE, EXECUTE, SHARE)
            val permissionDTO = PermissionDTO(snippetId, userId, ownerPermissions)
            val permissionEntity = permissionService.addPermission(permissionDTO)
            ResponseEntity.ok(permissionEntity)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/get")
    fun getPermissionById(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam permissionRequest: PermissionRequest
        ): ResponseEntity<Set<PermissionType>> {
        try {
            val userId = getUserIdFromJWT(jwt)
            val permissions = permissionService.getPermissions(permissionRequest.snippetId, userId)
            return ResponseEntity.ok(permissions.permissions)
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.status(500).body(null)
        }
    }

    @PostMapping("/share")
    fun sharePermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody shareRequest: ShareRequest
    ): ResponseEntity<PermissionEntity> {
        return try {
            val snippetId = shareRequest.snippetId
            val userId = shareRequest.friendId
            val permissionDTO = PermissionDTO(snippetId, userId, setOf(READ))
            ResponseEntity.ok(permissionService.updatePermission(permissionDTO))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }


    @GetMapping("/get_all")
    fun getAllSnippets(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<SnippetsGroup> {
        return try {
            val userId = getUserIdFromJWT(jwt)
            val snippets = permissionService.getSnippetsByUserId(userId)
            ResponseEntity.ok(SnippetsGroup(snippets))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    private fun getUserIdFromJWT(jwt: Jwt): String {
        val auth = OAuth2ResourceServerSecurityConfiguration(
            System.getenv("AUTH0_AUDIENCE"),
            System.getenv("AUTH_SERVER_URI"),
            System.getenv("UI_URL")
        ).jwtDecoder()
        return auth.decode(jwt.tokenValue).subject!!
    }
}