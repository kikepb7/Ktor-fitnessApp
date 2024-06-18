package com.example.application

import com.example.application.plugins.configureRouting
import com.example.application.plugins.configureSecurity
import com.example.application.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
}
