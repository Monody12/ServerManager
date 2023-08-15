package com.example.servermanager.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class WebController {
    @GetMapping
    fun index(): String {
        return "index.html"
    }

    @GetMapping("/ws")
    fun ws(): String {
        return "ws.html"
    }
}