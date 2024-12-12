package com.example.springboot.app.repository.entity
import com.example.springboot.app.utils.PermissionType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.util.UUID

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
        joinColumns = [JoinColumn(name = "id", referencedColumnName = "id")],
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    val permissions: Set<PermissionType> = emptySet(),
)
