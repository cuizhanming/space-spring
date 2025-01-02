package com.cuizhanming.rag.controller

import org.springframework.web.bind.annotation.*
import com.cuizhanming.rag.service.RagService
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.core.io.ClassPathResource
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartRequest
import java.net.URI

@RestController
@RequestMapping("/api/rag")
class RagController(private val ragService: RagService) {
    
    @PostMapping("/documents")
    fun addRemoteDocument(@RequestParam("pdf") content: MultipartFile) =
        ragService.loadResource(content.resource)

    @PostMapping("/local-documents")
    fun addLocalDocument(): ResponseEntity<Unit> {
        val resource = ClassPathResource("documents/llama2.pdf")
        ragService.loadResource(resource)
        return ok().build()
    }

    @GetMapping("/query")
    fun query(@RequestParam(name = "advisor") advisor: Boolean,
              @RequestParam(name = "message") message: String): String =
        if (advisor) ragService.queryWithAdvisor(message) else ragService.queryWithoutAdvisor(message)


    @GetMapping("/text")
    fun chat(@RequestParam(name = "message") message: String): String {
        return ragService.chat(message)
    }
}