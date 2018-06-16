package com.qautomatron.data

import io.ktor.auth.UserPasswordCredential

object UserManager {

    fun findUserByCredentials(credentials: UserPasswordCredential): User? {
        return Users.find { user -> user.name == credentials.name && user.pass == credentials.password }
    }

    fun findUserById(id: Int): User? {
        return Users.find { user -> user.id == id }
    }

    /**
     * Test Users collection
     */
    private val Users = listOf(
        User(1, "Bob", "SuperDuper"),
        User(2, "Sam", "DuperSuper")
    )
}