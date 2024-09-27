package com.cuizhanming.template.kotlin.springsecurity.business.service

import com.cuizhanming.template.kotlin.springsecurity.business.model.Article
import com.cuizhanming.template.kotlin.springsecurity.business.repository.ArticleRepository
import org.springframework.stereotype.Service
import java.util.UUID

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