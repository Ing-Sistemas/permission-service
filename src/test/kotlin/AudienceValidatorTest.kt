package com.example.springboot.app.auth

import org.junit.jupiter.api.Test
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class AudienceValidatorTest {

    private val audience = "expected-audience"
    private val validator = AudienceValidator(audience)

    @Test
    fun `validate should return success if audience is present`() {
        val jwt = mock(Jwt::class.java)
        `when`(jwt.audience).thenReturn(setOf(audience).toMutableList())

        val result = validator.validate(jwt)
        assertEquals(OAuth2TokenValidatorResult.success(), result)
    }

    @Test
    fun `validate should return failure if audience is missing`() {
        val jwt = mock(Jwt::class.java)
        `when`(jwt.audience).thenReturn(setOf("wrong-audience").toMutableList())

        val result = validator.validate(jwt)
        val expectedError = OAuth2Error("invalid_token", "The required audience is missing", null)

        // Verifica el estado de fallo y el error
        assertEquals(expectedError.errorCode, result.errors.firstOrNull()?.errorCode)
        assertEquals(expectedError.description, result.errors.firstOrNull()?.description)
    }

}
