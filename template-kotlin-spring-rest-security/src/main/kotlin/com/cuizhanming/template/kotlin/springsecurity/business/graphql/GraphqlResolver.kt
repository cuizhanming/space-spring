package com.cuizhanming.template.kotlin.springsecurity.business.graphql

//import org.springframework.graphql.data.method.annotation.MutationMapping
//import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GraphqlResolver {

//    @QueryMapping
//    @PreAuthorize("hasRole('ADMIN')")
    fun adminOnlyData(): String {
        return "This is secured data for ADMIN role"
    }

//    @QueryMapping
//    @PreAuthorize("isAuthenticated()")
    fun authenticatedData(): String {
        return "This data is available for authenticated users"
    }

//    @MutationMapping
//    @PreAuthorize("hasRole('USER')")
    fun updateUserData(): String {
        return "User data updated successfully"
    }
}