package com.cuizhanming.template.kotlin.springsecurity.business.controller

import com.cuizhanming.template.kotlin.springsecurity.business.model.Role
import com.cuizhanming.template.kotlin.springsecurity.business.model.User
import com.cuizhanming.template.kotlin.springsecurity.business.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

data class UserRequest(
    val email: String,
    val password: String
)

data class UserResponse(
    val id: String,
    val email: String,
    val role: String
) {
    constructor(id: String, email: String, role: Role) : this(id, email, role.name)
}


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

    private fun User.toResponse() = UserResponse(id.toString(), email, role)

    private fun UserRequest.toUser() = User(id = UUID.randomUUID(), email = email, password = password, role = Role.USER)

}

