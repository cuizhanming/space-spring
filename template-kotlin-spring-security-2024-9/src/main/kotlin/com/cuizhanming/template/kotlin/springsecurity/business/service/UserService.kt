package com.cuizhanming.template.kotlin.springsecurity.business.service

import com.cuizhanming.template.kotlin.springsecurity.business.model.User
import com.cuizhanming.template.kotlin.springsecurity.business.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(user: User) : User?{
        val found = userRepository.findByEmail(user.email)

        return if (found == null) {
            val saved = userRepository.save(user)
            saved
        } else null
    }

    fun findByUUID(id: UUID): User? {
        return userRepository.findByUUID(id)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun deleteByUUID(id: UUID) =
        userRepository.deleteByUUID(id)

}