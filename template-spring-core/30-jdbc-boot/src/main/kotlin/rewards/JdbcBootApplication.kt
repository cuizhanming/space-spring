package rewards

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootApplication
class JdbcBootApplication {
	@Bean
	fun commandLineRunner(jdbcTemplate: JdbcTemplate): CommandLineRunner {
		val query = "SELECT count(*) FROM T_ACCOUNT"
		return CommandLineRunner {
			val count = jdbcTemplate.queryForObject(query, Long::class.java)
			println("Number of accounts: $count")
		}
	}
}

fun main(args: Array<String>) {
	runApplication<JdbcBootApplication>(*args)
}

