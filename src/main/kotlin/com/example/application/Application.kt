package com.example.application

import com.example.application.plugins.*
import com.example.data.route.routeRoutine
import com.example.data.route.routeUser
import com.example.data.route.routeUserRoutine
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
    routeRoutine()
    routeUserRoutine()
//    configureMonitoring()
//    configureTemplating()
//    configureHTTP()
}

