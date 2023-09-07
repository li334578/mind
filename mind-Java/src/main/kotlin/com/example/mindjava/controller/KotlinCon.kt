package com.example.mindjava.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Date 2023-09-07 13:58
 * @Description kotlin controller
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@RequestMapping("/kotlin")
open class KotlinCon {

    @GetMapping("/lang")
    fun test(): String {
        return "Kotlin"
    }
}
