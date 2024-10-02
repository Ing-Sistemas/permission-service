package com.example.springboot.app.dao

import org.springframework.stereotype.Repository

@Repository //anotacion para que se reconozca como 'queryer'
class UserDao {
    // should have CRUD operations for the users permissions
    fun getPermissionById(userId: String): List<Boolean> {
        // mock data, deberia quieryar la base de datos
        return listOf(true, true, true, true)
    }
}