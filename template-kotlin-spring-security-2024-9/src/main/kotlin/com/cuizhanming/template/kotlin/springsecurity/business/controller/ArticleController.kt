package com.cuizhanming.template.kotlin.springsecurity.business.controller

import com.cuizhanming.template.kotlin.springsecurity.business.model.Article
import com.cuizhanming.template.kotlin.springsecurity.business.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

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

data class ArticleResponse (
    val id: UUID,
    val title: String,
    val content: String
)