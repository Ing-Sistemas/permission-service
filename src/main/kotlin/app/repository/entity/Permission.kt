package com.example.springboot.app.repository.entity

import jakarta.persistence.*


@Entity
@Table(name = "user_snippet_permissions")
data class Permission(
    @Id
    @Column(name = "snippet_id", nullable = false)
    val snippetId: String,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "permissions", nullable = false)
    val permissions: String
)