package com.qautomatron

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import com.qautomatron.data.User
import java.util.*

object JwtConfig {

    private const val secret = "someSecret" //secret for crypto
    private const val issuer = "ktor.io"
    const val audience = "jwt-audience-ktor-backend" //jwt receiver 'aud'
    const val realm = "jwt-ktor-backend"
    private const val validityInMs = 600000 // 10 minute

    private val algorithm = Algorithm.HMAC512(secret) // algo

    val verifier: JWTVerifier = JWT
            .require(algorithm)
            .withIssuer(issuer)
            .build()

    /**
     * Produce a token for User
     */
    fun makeToken(user: User): String = JWT.create()
            .withAudience(audience)
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .withExpiresAt(getExpiration())
            .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}