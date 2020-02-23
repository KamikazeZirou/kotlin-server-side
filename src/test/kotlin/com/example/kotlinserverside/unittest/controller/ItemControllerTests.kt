package com.example.kotlinserverside.unittest.controller

import com.example.kotlinserverside.entity.Item
import com.example.kotlinserverside.handler.ItemHandler
import com.example.kotlinserverside.repository.ItemRepository
import com.example.kotlinserverside.router.ApiRouter
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration(classes = [ApiRouter::class, ItemHandler::class])
@WebFluxTest
internal class ItemControllerTests(@Autowired private val webTestClient: WebTestClient) {
    @MockkBean lateinit var mockItemRepository: ItemRepository

    @DisplayName("全件取得")
    @Nested
    inner class GetList {
        private val getListUri = "/items"

        val testItems = listOf(
                Item(id = 1, name = "test1", price = 100),
                Item(id = 2, name = "test2", price = 200)
        )

        @DisplayName("正常系")
        @Test
        fun success() {
            every { mockItemRepository.findAll() } returns testItems

            val expectedResponse = """
                [
                    { "id": 1, "name": "test1", "price": 100 },
                    { "id": 2, "name": "test2", "price": 200 }
                ]
            """.trimIndent()

            webTestClient.get().uri(getListUri).exchange()
                    .expectStatus().isOk
                    .expectBody().json(expectedResponse)
            verify { mockItemRepository.findAll() }
        }
    }
}