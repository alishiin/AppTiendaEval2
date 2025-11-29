// UserRepository.kt
package com.example.apptiendaval2.model

data class User(
    val nombre: String,
    val email: String,
    val password: String
)

object UserRepository {
    private val users = mutableListOf<User>()
    private val userCredentials = mutableMapOf<String, String>()

    init {
        val testUser = User("Test User", "a@a.cl", "123123")
        users.add(testUser)
        userCredentials[testUser.email] = testUser.password
    }

    fun addUser(user: User) {
        users.add(user)
        userCredentials[user.email] = user.password
    }

    fun validateUser(email: String, password: String): Boolean {
        return userCredentials[email] == password
    }
}