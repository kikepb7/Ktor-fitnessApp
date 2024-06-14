package com.example.application.plugins

import com.example.data.route.routeUser
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routeUser()
}
