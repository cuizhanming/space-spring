package com.cuizhanming.template.kotlin.springsecurity.business.rest

import com.cuizhanming.template.kotlin.springsecurity.config.TestcontainersConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

@DataJpaTest
@Testcontainers
@Import(TestcontainersConfiguration::class)
internal class BookmarkRepositoryTest {
    @Autowired
    var bookmarkRepository: BookmarkRepository? = null

    @BeforeEach
    fun setUp() {
        bookmarkRepository!!.deleteAllInBatch()
    }

    @Test
    fun shouldGetAllBookmarksOrderByCreatedAtDesc() {
        bookmarkRepository!!.save(Bookmark(UUID.randomUUID(),"JetBrains Blog", "https://blog.jetbrains.com"))
        bookmarkRepository!!.save(Bookmark(UUID.randomUUID(),"IntelliJ IDEA Blog", "https://blog.jetbrains.com/idea/"))

        val bookmarks = bookmarkRepository!!.findAllByOrderByCreatedAtDesc()

        assertThat(bookmarks).hasSize(2)
        assertEquals("IntelliJ IDEA Blog", bookmarks[0].title)
        assertEquals("JetBrains Blog", bookmarks[1].title)
    }

}