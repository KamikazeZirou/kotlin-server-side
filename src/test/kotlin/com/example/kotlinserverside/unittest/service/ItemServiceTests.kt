package com.example.kotlinserverside.unittest.service

import com.example.kotlinserverside.entity.Item
import com.example.kotlinserverside.repository.ItemRepository
import com.example.kotlinserverside.service.ItemService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ItemServiceTests(@Autowired private val itemService: ItemService) {
    @MockkBean
    private lateinit var mockItemRepository: ItemRepository

    final val testItem1 = Item(id = 1, name = "test1", price = 100)
    final val testItem2 = Item(id = 2, name = "test2", price = 200)
    val testItems = listOf(testItem1, testItem2)

    @Test
    fun getItemsWithUpperCase() {
        every { mockItemRepository.findAll() } returns testItems

        val expectedItems = listOf(
                Item(id = 1, name = "TEST1", price = 100),
                Item(id = 2, name = "TEST2", price = 200)
        )

        assertThat(itemService.getItemsWithUpperCase())
                .isEqualTo(expectedItems)
        verify { mockItemRepository.findAll() }
    }
}