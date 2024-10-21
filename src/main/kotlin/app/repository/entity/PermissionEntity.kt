package com.example.springboot.app.repository.entity

import com.example.springboot.app.utils.PermissionType
import jakarta.persistence.*

@Entity
@Table(name = "permission_entity")
data class PermissionEntity(
    @Id
    @Column(name = "snippet_id", nullable = false)
    val snippetId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @ElementCollection(targetClass = PermissionType::class, fetch = FetchType.EAGER)
    @CollectionTable(
        name = "permission_entity_permissions",
        joinColumns = [JoinColumn(name = "snippet_id", referencedColumnName = "snippet_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    val permissions: Set<PermissionType> = emptySet()
)