package com.cuizhanming.rag.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.reader.tika.TikaDocumentReader
import org.springframework.ai.transformer.splitter.TokenTextSplitter
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class RagService(
    private val vectorStore: VectorStore,
    private val chatClient: ChatClient
) {

    fun loadResource(content: Resource) {
        val tikaDocumentReader = TikaDocumentReader(content)
        val splitter = TokenTextSplitter(1000, 400, 10, 5000, true)
        vectorStore.write(splitter.split(tikaDocumentReader.read()))
    }

    fun queryWithoutAdvisor(question: String): String {
        // Retrieve relevant documents
        val relevantDocs = vectorStore.similaritySearch(question)
        
        // Create context from retrieved documents
        val context = relevantDocs?.joinToString("\n") { it.text?:"" }
        
        // Create prompt with context
        val promptTemplate = """
            你是一个问答机器人。
            你的任务是根据下述给定的已知信息回答用户问题。
            
            已知信息:
            $context
            
            如果已知信息不包含用户问题的答案，或者已知信息不足以回答用户的问题，请直接回复"我无法回答您的问题"。
            请不要输出已知信息中不包含的信息或答案。
            请用中文回答用户问题。
        """.trimIndent()
        val systemPrompt = Prompt(promptTemplate)
        
        // Generate response
        return chatClient.prompt(systemPrompt).user(question).call().content().orEmpty()
    }

    fun queryWithAdvisor(question: String): String {
        val promptTemplate = """
            You are a helpful assistant. Use the following context to answer the question.
            If you cannot find the answer in the context, say so.
            Answer the question based on the advisor context.
        """
        val chatClient = chatClient.mutate().defaultAdvisors(QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build())).build()
        val response = chatClient.prompt(Prompt(promptTemplate))
            .user(question)
            .call().content()
        return response.toString()
    }

    fun chat(message: String): String {
        return chatClient.prompt().user(message).call().content().toString()
    }

} 