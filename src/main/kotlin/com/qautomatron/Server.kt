package com.qautomatron

import com.qautomatron.data.User
import com.qautomatron.data.UserManager
import com.qautomatron.data.response.ErrorData
import com.qautomatron.data.response.Token
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.util.decodeBase64
import mu.KotlinLogging
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

private var epoch = AtomicLong(Instant.now().epochSecond)
private val logger = KotlinLogging.logger {}
private val hashedUserTable = UserHashedTableAuth(table = mapOf(
        "test" to decodeBase64("VltM4nfheqcJSyH887H+4NEOm2tDuKCl83p5axYXlF0=") // sha256 for "test"
))

fun Application.module() {

    install(DefaultHeaders)
    install(CallLogging)
    install(CORS){
        host("localhost")
        header(HttpHeaders.Authorization)
    }
    install(Authentication) {
        basic("basic") {
            validate { hashedUserTable.authenticate(it) }
        }

        jwt("jwt") {
            verifier(JwtConfig.verifier)
            this.realm = JwtConfig.realm
            validate { credential ->
                if (credential.payload.audience.contains(JwtConfig.audience)) {
//                    credential.payload.getClaim("id").asInt()?.let { UserManager.findUserById(it)}
                    JWTPrincipal(credential.payload) }
                else
                    null
            }
        }
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {

        get("/epoch") {
            logger.info { "Requested number $epoch" }
            call.respondText(epoch.getAndIncrement().toString(), ContentType.Application.Json)
        }

        get("/status") {
            logger.info { "Requested status" }
            call.respondText("I'm alive!")
        }

        post("/login") {
            val credentials = call.receive<UserPasswordCredential>()

            val user = UserManager.findUserByCredentials(credentials)

            if (user != null) {
                val token = JwtConfig.makeToken(user)
                call.respond(Token(token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, ErrorData.Unauthorized)
            }
        }

        authenticate("jwt") {
            get("/auth/jwt") {
                logger.info { "Request with jwt auth" }
                val user = call.jwt
                call.respondText { "Hello you are Auth User by JWT Auth! \n " +
                        "Your internal user id is <${user.payload.getClaim("id").asInt()}>" }
            }
        }

        authenticate("basic") {

            get("/auth/basic") {
                logger.info { "Request with basic auth" }
                val user = call.user
                call.respondText { "Hello <${user.name}> you are Auth User by Basic Auth!" }
            }
        }
    }
}

private val ApplicationCall.user get() = authentication.principal<User>()!!
private val ApplicationCall.jwt get() = authentication.principal<JWTPrincipal>()!!