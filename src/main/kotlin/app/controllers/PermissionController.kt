package com.example.springboot.app.controllers
import com.example.springboot.app.auth.OAuth2ResourceServerSecurityConfiguration
import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.service.PermissionService
import com.example.springboot.app.utils.PermissionRequest
import com.example.springboot.app.utils.PermissionType.EXECUTE
import com.example.springboot.app.utils.PermissionType.READ
import com.example.springboot.app.utils.PermissionType.SHARE
import com.example.springboot.app.utils.PermissionType.WRITE
import com.example.springboot.app.utils.ShareRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PermissionController(private val permissionService: PermissionService) {
    private val logger = LoggerFactory.getLogger(PermissionController::class.java)

    @PostMapping("/create")
    fun createPermission(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody permissionRequest: PermissionRequest,
    ): ResponseEntity<PermissionDTO> {
        return try {
            logger.trace("Creating permission for snippet with id: ${permissionRequest.snippetId}")
            logger.info("Creating permission for snippet with id: ${permissionRequest.snippetId}")
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
        @PathVariable snippetId: String,
    ): ResponseEntity<PermissionDTO> {
        try {
            logger.trace("Getting permissions for snippet with id: $snippetId")
            logger.info("Getting permissions for snippet with id: $snippetId")
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
        @RequestBody shareRequest: ShareRequest,
    ): ResponseEntity<PermissionDTO> {
        return try {
            logger.trace("Sharing snippet with id: ${shareRequest.snippetId} with user: ${shareRequest.friendId}")
            logger.info("Sharing snippet with id: ${shareRequest.snippetId} with user: ${shareRequest.friendId}")
            val snippetId = shareRequest.snippetId
            val userId = shareRequest.friendId
            val permissionDTO = PermissionDTO(snippetId, userId, setOf(WRITE))
            ResponseEntity.ok(translate(permissionService.updatePermission(permissionDTO)))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    @GetMapping("/get_all")
    fun getAllSnippets(
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<String>> {
        return try {
            logger.trace("Getting all snippets")
            logger.info("Getting all snippets")
            val userId = getUserIdFromJWT(jwt)
            val snippets = permissionService.getSnippetsByUserId(userId)
            ResponseEntity.ok(snippets)
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(null)
        }
    }

    private fun getUserIdFromJWT(jwt: Jwt): String {
        val auth =
            OAuth2ResourceServerSecurityConfiguration(
                System.getenv("AUTH0_AUDIENCE"),
                System.getenv("AUTH_SERVER_URI"),
            ).jwtDecoder()
        val subject = auth.decode(jwt.tokenValue).subject
        return subject?.removePrefix("auth0|") ?: throw IllegalArgumentException("User ID is null or invalid")
    }

    private fun translate(permissionEntity: PermissionEntity): PermissionDTO {
        return PermissionDTO(
            permissionEntity.snippetId,
            permissionEntity.userId,
            permissionEntity.permissions,
        )
    }

    @PostMapping("/delete")
    fun deletePermissions(
        @RequestBody permissionRequest: PermissionRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<Int> {
        logger.trace("Deleting snippet with id: ${permissionRequest.snippetId}")
        logger.info("Deleting snippet with id: ${permissionRequest.snippetId}")
        val userId = getUserIdFromJWT(jwt)
        return try {
            ResponseEntity.ok(permissionService.deleteSnippetByIdAndUserId(userId, permissionRequest.snippetId))
        } catch (e: Exception) {
            println(e.message)
            ResponseEntity.status(500).body(0)
        }
    }

    @GetMapping("/correlate/{cId}")
    fun correlation(
        @PathVariable cId: String,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<Void> {
        logger.info("Correlation ID: $cId")
        return ResponseEntity.ok().build()
    }
}
