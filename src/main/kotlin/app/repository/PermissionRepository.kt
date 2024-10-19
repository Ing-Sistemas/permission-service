package com.example.springboot.app.repository

import com.example.springboot.app.repository.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission, Long> {
    fun findByUserId(userId: String): Permission
    fun findBySnippetId(snippetId: String): Permission
}