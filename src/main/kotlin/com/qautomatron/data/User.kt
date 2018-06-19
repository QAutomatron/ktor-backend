package com.qautomatron.data

import com.qautomatron.data.response.UserResponse
import io.ktor.auth.*

data class User(
        val id: Int,
        val name: String,
        val pass: String
) : Principal {

    fun toResponse() = UserResponse(
            id = id,
            name = name)
}