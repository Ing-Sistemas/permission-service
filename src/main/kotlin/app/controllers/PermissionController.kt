package com.example.springboot.app.controllers

import com.example.springboot.app.repository.entity.Permission
import com.example.springboot.app.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono

@Controller("/api/permission")
class PermissionController(private val permissionService: PermissionService) {

    @GetMapping("/get-permission")
    fun getPermissionById(@RequestBody id: String): ResponseEntity<String> {
        return permissionService.getPermissionsByUserId(id)
    }
}