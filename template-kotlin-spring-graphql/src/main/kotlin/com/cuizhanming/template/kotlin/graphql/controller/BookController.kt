package com.cuizhanming.template.kotlin.graphql.controller

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class BookController {

    val books = listOf(
        Book("book-1", "Effective Java", 416, "author-1"),
        Book("book-2", "Hitchhiker's Guide to the Galaxy", 208, "author-2"),
        Book("book-3", "Down Under", 436, "author-3")
    )

    val authors = listOf(
        Author("author-1", "Joshua", "Bloch"),
        Author("author-2", "Douglas", "Adams"),
        Author("author-3", "Bill", "Bryson")
    )

    @QueryMapping
    fun bookById(@Argument id: String?): Book =
        books.first { it.id == id }

    @SchemaMapping
    fun author(book: Book): Author =
        authors.first { it.id == book.authorId }
}

data class Book(val id: String, val name: String, val pageCount: Int, val authorId: String)

data class Author(val id: String, val firstName: String, val lastName: String)