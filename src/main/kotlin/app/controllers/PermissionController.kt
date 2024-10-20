package com.example.springboot.app.controllers

import com.example.springboot.app.repository.entity.Permission
import com.example.springboot.app.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api")
class PermissionController(private val permissionService: PermissionService) {

    @GetMapping("get")
    fun getPermissionById(@RequestBody id: String): ResponseEntity<List<Boolean>> {
        return permissionService.getPermissionById(id)
    }
}