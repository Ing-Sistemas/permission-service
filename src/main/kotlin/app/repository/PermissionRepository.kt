package com.example.springboot.app.repository

import com.example.springboot.app.repository.entity.PermissionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<PermissionEntity, String> {
    fun findByUserIdAndSnippetId(userId: String, snippetId: String): PermissionEntity
    fun findBySnippetId(snippetId: String): PermissionEntity
}