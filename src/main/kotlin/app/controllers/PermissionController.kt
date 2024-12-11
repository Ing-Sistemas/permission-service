package com.example.springboot.app.controllers

import com.example.springboot.app.auth.OAuth2ResourceServerSecurityConfiguration
import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.service.PermissionService
import com.example.springboot.app.utils.PermissionRequest
import org.springframework.http.ResponseEntity
import com.example.springboot.app.utils.PermissionType.*
import com.example.springboot.app.utils.ShareRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PermissionController(private val permissionService: PermissionService) {

    private val logger = LoggerFactory.getLogger(PermissionController::class.java)

    @PostMapping("/create")
    fun createPermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody permissionRequest: PermissionRequest
    ): ResponseEntity<PermissionDTO> {
        return try {
            val snippetId = permissionRequest.snippetId
            val userId = getUserIdFromJWT(jwt)
            val ownerPermissions = setOf(READ, WRITE, EXECUTE, SHARE)
            val permissionDTO = PermissionDTO(snippetId, userId, ownerPermissions)
            val permissionEntity = permissionService.addPermission(permissionDTO)
            ResponseEntity.ok(translate(permissionEntity))
        } catch (e: Exception) {
            logger.error(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/{snippetId}")
    fun getPermissionById(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable snippetId: String
        ): ResponseEntity<PermissionDTO> {
        try {
            val userId = getUserIdFromJWT(jwt)
            val permissions = permissionService.getPermissions(snippetId, userId)
            println(permissions)
            return ResponseEntity.ok(translate(permissions))
        } catch (e: Exception) {
            println(e.message)
            return ResponseEntity.status(500).body(null)
        }
    }

    @PostMapping("/share")
    fun sharePermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody shareRequest: ShareRequest
    ): ResponseEntity<PermissionDTO> {
        return try {
            val snippetId = shareRequest.snippetId
            val userId = shareRequest.friendId
            val permissionDTO = PermissionDTO(snippetId, userId, setOf(READ))
            ResponseEntity.ok(translate(permissionService.updatePermission(permissionDTO)))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }


    @GetMapping("/get_all")
    fun getAllSnippets(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<String>> {
        return try {
            val userId = getUserIdFromJWT(jwt)
            val snippets = permissionService.getSnippetsByUserId(userId)
            logger.info("Snippets: $snippets")
            ResponseEntity.ok(snippets)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    private fun getUserIdFromJWT(jwt: Jwt): String {
        val auth = OAuth2ResourceServerSecurityConfiguration(
            System.getenv("AUTH0_AUDIENCE"),
            System.getenv("AUTH_SERVER_URI")
        ).jwtDecoder()
        val subject = auth.decode(jwt.tokenValue).subject
        return subject?.removePrefix("auth0|") ?: throw IllegalArgumentException("User ID is null or invalid")
    }

    private fun translate(permissionEntity: PermissionEntity) : PermissionDTO {
        return PermissionDTO(
            permissionEntity.snippetId,
            permissionEntity.userId,
            permissionEntity.permissions
        )
    }

    @PostMapping("/delete")
    fun deletePermissions(
        @RequestBody permissionRequest: PermissionRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<Int> {
        val userId = getUserIdFromJWT(jwt)
        return try {
            ResponseEntity.ok(permissionService.deleteSnippetByIdAndUserId(userId, permissionRequest.snippetId))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(0)
        }
    }
}