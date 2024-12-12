package com.example.springboot.app.repository.entity

import com.example.springboot.app.utils.PermissionType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PermissionEntityTest {
    @Test
    fun `should create PermissionEntity with all fields`() {
        val permissions = setOf(PermissionType.READ, PermissionType.WRITE)
        val permissionEntity =
            PermissionEntity(
                snippetId = "snippet123",
                userId = "user456",
                permissions = permissions,
            )

        assertNotNull(permissionEntity)
        assertEquals("snippet123", permissionEntity.snippetId)
        assertEquals("user456", permissionEntity.userId)
        assertEquals(permissions, permissionEntity.permissions)
    }

    @Test
    fun `should create PermissionEntity with default permissions`() {
        val permissionEntity =
            PermissionEntity(
                snippetId = "snippet789",
                userId = "user987",
            )

        assertNotNull(permissionEntity)
        assertEquals("snippet789", permissionEntity.snippetId)
        assertEquals("user987", permissionEntity.userId)
        assertEquals(emptySet<PermissionType>(), permissionEntity.permissions)
    }

    @Test
    fun `should handle empty snippetId and userId`() {
        val permissionEntity =
            PermissionEntity(
                snippetId = "",
                userId = "",
                permissions = setOf(PermissionType.EXECUTE),
            )

        assertNotNull(permissionEntity)
        assertEquals("", permissionEntity.snippetId)
        assertEquals("", permissionEntity.userId)
        assertEquals(setOf(PermissionType.EXECUTE), permissionEntity.permissions)
    }
}
