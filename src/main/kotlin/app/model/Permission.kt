package com.example.springboot.app.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    val id: Int? = null,
    @NotNull
    val userId: String,
    @NotNull
    val snippetId: String,
    @NotNull
    val read: Boolean,
    @NotNull
    val write: Boolean,
    @NotNull
    val execute: Boolean,
    @NotNull
    val share: Boolean

    // convertir rwxs en una clase q los maneje y cambiarle eno nombre a permissionInfo o similar
)