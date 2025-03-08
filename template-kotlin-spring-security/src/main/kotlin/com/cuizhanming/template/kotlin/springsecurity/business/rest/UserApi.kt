package com.cuizhanming.template.kotlin.springsecurity.business.rest

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/users")
class UserController (val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): UserResponse? =
        userService.createUser(userRequest.toUser())
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create user")

    @GetMapping
    fun findAll(): List<UserResponse> =
        userService.findAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun findByUUID(@PathVariable id: UUID): UserResponse? =
        userService.findByUUID(id)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user")

    @DeleteMapping("/{id}")
    fun deleteByUUID(@PathVariable id: UUID): ResponseEntity<Boolean> {
        val success = userService.deleteByUUID(id)
        return if (success)
            ResponseEntity.noContent().build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find user")
    }

    private fun User.toResponse() = UserResponse(id.toString(), name, email, role)

    private fun UserRequest.toUser() = User(id = UUID.randomUUID(), name = name, email = email, password = password, role = Role.USER)

}


@Service
class UserService(
    private val userRepository: UserRepository,
    private val jpaUserRepository: JpaUserRepository
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
        return jpaUserRepository.findAllByOrderByNameAsc()
    }

    fun deleteByUUID(id: UUID) =
        userRepository.deleteByUUID(id)

}

@Repository("jpaUserRepository")
interface JpaUserRepository: JpaRepository<User, UUID> {
    fun findAllByOrderByNameAsc(): List<User>
}

@Repository("userRepository")
class UserRepository (
    private val encoder: PasswordEncoder
){

    private val users = mutableListOf<User>(
        User(
            UUID.randomUUID(), "user1", "email1@gmail.com", encoder.encode("password1"), Role.ADMIN
        ),
        User(
            UUID.randomUUID(), "user2", "email2@gmail.com", encoder.encode("password2"), Role.USER
        ),
        User(
            UUID.randomUUID(), "user3", "email3@gmail.com", encoder.encode("password3"), Role.USER
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

data class UserRequest(
    val name: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: String
) {
    constructor(id:String, name:String, email:String, role:Role) : this(id, name, email, role.name)
}

@Entity
@Table(name = "users")
data class User(
    @Id
    var id: UUID,
    val name: String,
    val email: String,
    val password: String,
    val role: Role
) {
}

enum class Role {
    USER, ADMIN
}
