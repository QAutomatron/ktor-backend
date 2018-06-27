package com.qautomatron

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testStatus()  {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/status").apply {
                assertEquals(200, response.status()?.value)
                assertEquals(response.content, "I'm alive!")
            }
        }
    }
}