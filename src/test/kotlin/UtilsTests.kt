package com.example.springboot.app.utils

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UtilsTests {

    @Test
    fun `PermissionRequest should store correct values`() {
        val request = PermissionRequest("123")
        assertEquals("123", request.snippetId)
    }

    @Test
    fun `ShareRequest should store correct values`() {
        val request = ShareRequest("123", "friend1")
        assertEquals("123", request.snippetId)
        assertEquals("friend1", request.friendId)
    }

    @Test
    fun `SnippetsGroup should store correct snippets`() {
        val group = SnippetsGroup(listOf("snippet1", "snippet2"))
        assertEquals(listOf("snippet1", "snippet2"), group.snippets)
    }
}
