package com.qautomatron.data

import io.ktor.auth.*

data class User(
        val id: Int,
        val name: String,
        val pass: String
) : Principal