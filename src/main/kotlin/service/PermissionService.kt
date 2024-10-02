package com.example.springboot.service

import com.example.springboot.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PermissionService {

    @Autowired
    val userDao= UserDao()

    fun getPermissionById(userId: String): ResponseEntity<List<Boolean>> {
        val permission = userDao.getPermissionById(userId)
        return ResponseEntity.ok(permission)
    }
}