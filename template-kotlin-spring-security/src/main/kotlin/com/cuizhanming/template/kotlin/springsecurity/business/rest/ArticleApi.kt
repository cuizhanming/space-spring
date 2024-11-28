package com.cuizhanming.template.kotlin.springsecurity.business.rest

import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


data class Article (
    var id: UUID,
    val title: String,
    val content: String
)

data class ArticleResponse (
    val id: UUID,
    val title: String,
    val content: String
)

@RestController
@RequestMapping("/api/articles")
class ArticleController (
        val articleService: ArticleService
) {

    @GetMapping
    fun findAll(): List<ArticleResponse> =
        articleService.findAll().map { it.toResponse() }

    private fun Article.toResponse() = ArticleResponse(id, title, content)
}

@Service
class ArticleService (val articleRepository: ArticleRepository) {

    fun save(article: Article): Article {
        return articleRepository.save(article)
    }

    fun findAll(): List<Article> {
        return articleRepository.findAll()
    }

    fun findById(id: UUID): Article? {
        return articleRepository.findById(id)
    }

    fun update(article: Article): Article {
        return articleRepository.update(article)
    }

    fun deleteById(id: UUID) {
        articleRepository.deleteById(id)
    }
}

@Repository
class ArticleRepository {

    private val articles = mutableListOf(
        Article(UUID.randomUUID(), "Kotlin", "Statically typed programming language for modern multiplatform applications"),
        Article(UUID.randomUUID(), "Spring Boot", "Makes it easy to create stand-alone, production-grade Spring based Applications"),
        Article(UUID.randomUUID(), "Spring Security", "Powerful and highly customizable authentication and access-control framework")
    )

    fun save(article: Article): Article {
        articles.add(article)
        return article
    }

    fun findAll(): List<Article> {
        return articles
    }

    fun findById(id: UUID): Article? {
        return articles.find { it.id == id }
    }

    fun update(article: Article): Article {
        val index = articles.indexOfFirst { it.id == article.id }
        articles[index] = article
        return article
    }

    fun deleteById(id: UUID) {
        articles.removeIf { it.id == id }
    }
}
