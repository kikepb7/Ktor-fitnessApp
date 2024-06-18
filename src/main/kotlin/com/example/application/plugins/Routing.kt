package com.example.application.plugins

import com.example.infrastructure.db.DbConnection
import com.example.api.route.routeRoutine
import com.example.api.route.routeUser
import com.example.api.route.routeUserRoutine
import com.example.data.repository.UserRepositoryImpl
import io.ktor.server.application.*

fun Application.configureRouting() {
    val db = DbConnection.getDatabaseInstance()
    val userRepositoryImpl = UserRepositoryImpl()

    routeUser(db, userRepositoryImpl)
    routeRoutine(db)
    routeUserRoutine(db)
}
