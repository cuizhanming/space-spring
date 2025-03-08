package com.cuizhanming.template.kotlin.springsecurity.business.rest

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "bookmark")
data class Bookmark(
    @Id
    val id: UUID,
    val title: String,
    val url: String,
    val createdAt: OffsetDateTime
)

interface BookmarkRepository: JpaRepository<Bookmark, UUID> {
    fun findAllByOrderByCreatedAtDesc(): List<Bookmark>
}

@RestController
@RequestMapping("/api/bookmarks")
class BookmarkController(
    val bookmarkRepository: BookmarkRepository
) {

    @GetMapping
    fun getBookmarks(): List<Bookmark> =
        bookmarkRepository.findAllByOrderByCreatedAtDesc()

}