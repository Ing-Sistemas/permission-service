package com.example.springboot.app.repository.entity

import com.example.springboot.app.utils.PermissionType
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "permission_entity")
data class PermissionEntity(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "snippet_id", nullable = false)
    val snippetId: String,

    @ElementCollection(targetClass = PermissionType::class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "permission_entity_permissions",
        joinColumns = [JoinColumn(name = "id", referencedColumnName = "id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    val permissions: Set<PermissionType> = emptySet()
)