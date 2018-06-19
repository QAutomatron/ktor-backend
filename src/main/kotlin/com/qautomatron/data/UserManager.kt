package com.qautomatron.data

import io.ktor.auth.UserPasswordCredential

object UserManager: UserSource {

    override fun findUserByCredentials(credentials: UserPasswordCredential): User? {
        return Users.find { user -> user.name.equals(credentials.name, ignoreCase = true) && user.pass == credentials.password }
    }

    override fun findUserById(id: Int): User? {
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