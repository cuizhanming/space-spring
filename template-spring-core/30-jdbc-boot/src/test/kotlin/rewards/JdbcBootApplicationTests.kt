package rewards

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest
class JdbcBootApplicationTests {

	companion object {
		const val QUERY = "SELECT count(*) FROM T_ACCOUNT"
	}

	@Autowired
	lateinit var jdbcTemplate: JdbcTemplate

	@Test
	fun contextLoads() {
	}

	@Test
	fun testNumberOfAccounts() {
		val count = jdbcTemplate.queryForObject(QUERY, Long::class.java)
		assertThat(count).isEqualTo(21L)
	}

}
