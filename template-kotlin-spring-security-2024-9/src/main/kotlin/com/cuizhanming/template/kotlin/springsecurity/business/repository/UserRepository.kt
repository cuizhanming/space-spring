package com.cuizhanming.template.kotlin.springsecurity.business.repository

import com.cuizhanming.template.kotlin.springsecurity.business.model.Role
import com.cuizhanming.template.kotlin.springsecurity.business.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository (
    private val encoder: PasswordEncoder
){

    private val users = mutableListOf<User>(
        User(
            UUID.randomUUID(), "email1@gmail.com", encoder.encode("password1"), Role.ADMIN
        ),
        User(
            UUID.randomUUID(), "email2@gmail.com", encoder.encode("password2"), Role.USER
        ),
        User(
            UUID.randomUUID(), "email3@gmail.com", encoder.encode("password3"), Role.USER
        ),
    )

    fun findAll(): List<User> {
        return users
    }

    fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    fun findByUUID(id: UUID): User? {
        return users.find { it.id == id }
    }

    fun save(user: User): User {
        val newUser = user.copy(id = UUID.randomUUID(), password = encoder.encode(user.password))
        users.add(newUser)
        return newUser
    }

    fun deleteByEmail(email: String) =
        users.removeIf { it.email == email }


    fun deleteByUUID(id: UUID) : Boolean =
        users.removeIf { it.id == id }

    fun update(user: User) {
        val index = users.indexOfFirst { it.email == user.email }
        if (index != -1) {
            users[index] = user
        }
    }
}