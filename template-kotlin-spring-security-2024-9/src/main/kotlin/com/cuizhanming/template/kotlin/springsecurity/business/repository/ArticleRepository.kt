package com.cuizhanming.template.kotlin.springsecurity.business.repository

import com.cuizhanming.template.kotlin.springsecurity.business.model.Article
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ArticleRepository {

    private val articles = listOf(
        Article(UUID.randomUUID(), "Kotlin", "Statically typed programming language for modern multiplatform applications"),
        Article(UUID.randomUUID(), "Spring Boot", "Makes it easy to create stand-alone, production-grade Spring based Applications"),
        Article(UUID.randomUUID(), "Spring Security", "Powerful and highly customizable authentication and access-control framework")
    ).toMutableList(
    )//mutableListOf<Article>()


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