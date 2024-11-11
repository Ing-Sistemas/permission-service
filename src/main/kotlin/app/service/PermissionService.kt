package com.example.springboot.app.service

import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.PermissionRepository
import com.example.springboot.app.repository.entity.PermissionEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PermissionService @Autowired constructor(
    private val permissionRepository: PermissionRepository
) {

//    fun getPermissionsByUserId(userId: String): String {
//        val permission = permissionRepository.findByUserIdAndSnippetId(userId)
//        return ResponseEntity.ok(permission.permissions)
//    }

    fun addPermission(permissionDTO: PermissionDTO): PermissionEntity {
        val permissionEntity = translate(permissionDTO)
        println(permissionEntity.permissions)
        return permissionRepository.save(permissionEntity)
    }

    fun updatePermission(permissionDTO: PermissionDTO): PermissionEntity {
        return permissionRepository.save(translate(permissionDTO))
    }

    fun getPermissions(snippetId: String, userId: String): PermissionEntity {
        return permissionRepository.findByUserIdAndSnippetId(userId, snippetId)
    }

    fun getSnippetsByUserId(userId: String): List<String> {
        return permissionRepository.findSnippetIdsByUserIdAndOwnerPermissions(userId)
    }

    private fun translate(permissionDTO: PermissionDTO): PermissionEntity{
        return PermissionEntity(
            snippetId = permissionDTO.snippetId,
            userId = permissionDTO.userId,
            permissions = permissionDTO.permissions
        )
    }
}