package com.example.springboot.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Snippet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val userId: String,
    val snippetId: String,
    val read: Boolean,
    val write: Boolean,
    val execute: Boolean,
    val share: Boolean
)