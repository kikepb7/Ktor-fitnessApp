package com.example.api.route

import com.example.data.entity.UserRoutineEntity
import com.example.api.response.GenericResponse
import com.example.domain.model.UserRoutineModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.routeUserRoutine(
    db: Database
) {

    routing {
        // CREATE
        post("/user-routine") {
            try {
                val userRoutineModel = call.receive<UserRoutineModel>()
                transaction(db) {
                    val userRoutineId = UserRoutineEntity.insert {
                        it[userId] = userRoutineModel.userId
                        it[routineId] = userRoutineModel.routineId
                    } get UserRoutineEntity.id
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    GenericResponse(isSuccess = false, data = "Internal server error: ${e.message}")
                )
            }
        }
    }
}