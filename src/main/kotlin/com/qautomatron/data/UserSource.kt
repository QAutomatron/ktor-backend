package com.qautomatron.data

import io.ktor.auth.UserPasswordCredential

interface UserSource {

    fun findUserByCredentials(credentials: UserPasswordCredential): User?
    fun findUserById(id: Int): User?

}