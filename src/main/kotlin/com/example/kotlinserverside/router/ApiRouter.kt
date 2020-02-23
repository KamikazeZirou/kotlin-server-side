package com.example.kotlinserverside.router

import com.example.kotlinserverside.handler.ItemHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

class ApiRouter(private val itemHandler: ItemHandler) {
    @Bean
    fun itemRouter() = router {
        ("/items" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/", itemHandler::getList)
            GET("/{id}", itemHandler::getById)
            POST("/", itemHandler::create)
            DELETE("/{id}", itemHandler::deleteById)
        }
    }
}