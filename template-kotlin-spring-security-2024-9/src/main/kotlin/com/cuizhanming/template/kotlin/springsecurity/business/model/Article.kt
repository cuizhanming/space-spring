package com.cuizhanming.template.kotlin.springsecurity.business.model

import java.util.UUID

data class Article (
    var id: UUID,
    val title: String,
    val content: String
)