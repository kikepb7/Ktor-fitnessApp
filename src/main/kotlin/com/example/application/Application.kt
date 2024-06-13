package com.example.application

import com.example.application.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureTemplating()
    configureHTTP()
    configureSecurity()
    configureRouting()
}
