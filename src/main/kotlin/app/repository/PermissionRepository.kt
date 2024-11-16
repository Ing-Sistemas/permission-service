package com.example.springboot.app.repository

import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.utils.PermissionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<PermissionEntity, String> {
    fun findByUserIdAndSnippetId(userId: String, snippetId: String): PermissionEntity
    fun findBySnippetId(snippetId: String): PermissionEntity
    @Query(
        "SELECT p.snippetId FROM PermissionEntity p WHERE p.userId = :userId AND " +
                "(:readPermission MEMBER OF p.permissions AND :writePermission MEMBER OF p.permissions)"
    )
    fun findSnippetIdsByUserIdAndOwnerPermissions(
        @Param("userId") userId: String,
        @Param("readPermission") readPermission: PermissionType = PermissionType.READ,
        @Param("writePermission") writePermission: PermissionType = PermissionType.WRITE
    ): List<String>
}