package com.example.servermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerManagerApplication

fun main(args: Array<String>) {
    runApplication<ServerManagerApplication>(*args)
}
