package com.example.springboot.controllers

import com.example.springboot.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller("permissions")
class PermissionController {

    @Autowired
    val permissionService = PermissionService()

    @GetMapping("get")
    fun getPermissionById(@RequestBody id: String): ResponseEntity<List<Boolean>> {
        return permissionService.getPermissionById(id)
    }

}