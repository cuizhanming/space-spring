package com.cuizhanming.template.templatekotlinspringboot2023

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter


fun main(args: Array<String>) {
	runApplication<TemplateKotlinSpringboot2023Application>(*args)
}

@SpringBootApplication
class TemplateKotlinSpringboot2023Application {

	@Bean
	fun runner(customRepository: CustomRepository) = ApplicationRunner {
		runBlocking {
			val customers: Flow<Customer> = flowOf("Alice", "Bob", "Carol", "Dave").map { Customer(name = it) }
			customRepository.saveAll(customers).collect{ println(it) }
		}
	}

	@Bean
	fun http(customRepository: CustomRepository) = coRouter {
		GET("/customer") {
			ok().bodyAndAwait(customRepository.findAll())
		}

		GET("/customer/{id}") {
			val id = it.pathVariable("id").toInt()
			customRepository.findById(id)?.let { customer -> ok().bodyValueAndAwait(customer) } ?: notFound().buildAndAwait()
		}
	}
}

interface CustomRepository : CoroutineCrudRepository<Customer, Int>

data class Customer(@Id val id: Int? = null, val name: String? = null)