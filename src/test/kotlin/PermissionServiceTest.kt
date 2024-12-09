package com.example.springboot.app.service

import com.example.springboot.app.dto.PermissionDTO
import com.example.springboot.app.repository.PermissionRepository
import com.example.springboot.app.repository.entity.PermissionEntity
import com.example.springboot.app.utils.PermissionType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PermissionServiceTest {

    @Mock
    private lateinit var permissionRepository: PermissionRepository

    @InjectMocks
    private lateinit var permissionService: PermissionService

    private lateinit var permissionDTO: PermissionDTO
    private lateinit var permissionEntity: PermissionEntity

    @BeforeEach
    fun setUp() {
        permissionDTO = PermissionDTO(
            snippetId = "123",
            userId = "user1",
            permissions = setOf(PermissionType.READ, PermissionType.WRITE)
        )

        permissionEntity = PermissionEntity(
            snippetId = "123",
            userId = "user1",
            permissions = setOf(PermissionType.READ, PermissionType.WRITE)
        )
    }

    @Test
    fun `addPermission should save and return the permission entity`() {
        `when`(permissionRepository.save(permissionEntity)).thenReturn(permissionEntity)
        val result = permissionService.addPermission(permissionDTO)
        assertEquals(permissionEntity, result)
        verify(permissionRepository).save(permissionEntity)
    }

    @Test
    fun `updatePermission should update and return the permission entity`() {
        `when`(permissionRepository.save(permissionEntity)).thenReturn(permissionEntity)
        val result = permissionService.updatePermission(permissionDTO)
        assertEquals(permissionEntity, result)
        verify(permissionRepository).save(permissionEntity)
    }

    @Test
    fun `getPermissions should return a permission entity`() {
        `when`(permissionRepository.findByUserIdAndSnippetId("user1", "123")).thenReturn(permissionEntity)
        val result = permissionService.getPermissions("123", "user1")
        assertEquals(permissionEntity, result)
        verify(permissionRepository).findByUserIdAndSnippetId("user1", "123")
    }

    @Test
    fun `getSnippetsByUserId should return a list of snippet IDs`() {
        val snippetIds = listOf("123", "456")
        `when`(permissionRepository.findSnippetIdsByUserIdAndOwnerPermissions("user1"))
            .thenReturn(snippetIds)

        val result = permissionService.getSnippetsByUserId("user1")
        assertEquals(snippetIds, result)
        verify(permissionRepository).findSnippetIdsByUserIdAndOwnerPermissions("user1")
    }

    @Test
    fun `deleteSnippetByIdAndUserId should delete the permission and return result`() {
        `when`(permissionRepository.deleteByUserIdAndSnippetId("user1", "123")).thenReturn(1)

        val result = permissionService.deleteSnippetByIdAndUserId("user1", "123")
        assertEquals(1, result)
        verify(permissionRepository).deleteByUserIdAndSnippetId("user1", "123")
    }

    @Test
    fun `deleteSnippetByIdAndUserId should return 0 if no permission found`() {
        `when`(permissionRepository.deleteByUserIdAndSnippetId("user1", "999")).thenReturn(0)

        val result = permissionService.deleteSnippetByIdAndUserId("user1", "999")
        assertEquals(0, result)
        verify(permissionRepository).deleteByUserIdAndSnippetId("user1", "999")
    }
}
