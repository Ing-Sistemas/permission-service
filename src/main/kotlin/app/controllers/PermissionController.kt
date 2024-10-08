package com.example.springboot.app.controllers

import com.example.springboot.app.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono

@Controller("api")
class PermissionController(private val permissionService: PermissionService) {

    @GetMapping("get")
    fun getPermissionById(@RequestBody id: String): ResponseEntity<List<Boolean>> {
        return permissionService.getPermissionById(id)
    }

    @GetMapping("ping")
    fun pongSnippetService(): Mono<String> {
        return Mono.just("pong from permission service")
    }

}