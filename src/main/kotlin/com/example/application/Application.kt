package com.example.application

import com.example.application.plugins.*
import com.example.data.route.routeUser
import io.ktor.server.application.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}


//fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


fun Application.module() {
//    configureDatabase()
    configureSerialization()
    configureSecurity()
    configureRouting()
    routeUser()
//    configureMonitoring()
//    configureTemplating()
//    configureHTTP()
}

