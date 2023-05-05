package com.cuizhanming.template.templatekotlinspringboot2023

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TemplateKotlinSpringboot2023ApplicationTests (@Autowired val cr: CustomRepository) {

	@Test
	fun contextLoads() = runBlocking {
		cr.save(Customer(null, "Foo"))
		val customers = cr.findAll()
		Assertions.assertEquals(5, customers.count())
		Assertions.assertNotNull(customers.last().id)
	}

}
