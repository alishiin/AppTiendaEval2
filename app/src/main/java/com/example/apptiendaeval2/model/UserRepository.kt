// UserRepository.kt
package com.example.apptiendaval2.model

data class User(
    val nombre: String,
    val email: String,
    val password: String
)

object UserRepository {
    private val users = mutableListOf<User>()

    init {
        users.add(User("Test User", "a@a.cl", "123123"))
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun validateUser(email: String, password: String): Boolean {
        return users.any { it.email == email && it.password == password }
    }
}