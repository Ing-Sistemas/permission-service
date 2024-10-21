package com.example.springboot.app.repository

import com.example.springboot.app.repository.entity.PermissionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<PermissionEntity, Long> {
    fun findByUserIdAndSnippetId(userId: String, snippetId: Long): PermissionEntity
    fun findBySnippetId(snippetId: Long): PermissionEntity
}