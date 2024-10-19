package com.example.springboot.app.service

import com.example.springboot.app.repository.PermissionRepository
import com.example.springboot.app.repository.entity.Permission
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PermissionService @Autowired constructor(
    private val permissionRepository: PermissionRepository
) {

    fun getPermissionsByUserId(userId: String): ResponseEntity<String> {
        val permission = permissionRepository.findByUserId(userId)
        return ResponseEntity.ok(permission.permissions)
    }

    fun addPermission(userId: String, snippetId: String, permissions: String): ResponseEntity<String> {
        val userSnippetPermission = Permission(userId = userId, snippetId = snippetId, permissions = permissions)
        val savedPermission = permissionRepository.save(userSnippetPermission)
        return ResponseEntity.ok(savedPermission.permissions)
    }

    fun hasPermission(permissionString: String, permission: Char): Boolean {
        return permissionString.contains(permission)
    }
}