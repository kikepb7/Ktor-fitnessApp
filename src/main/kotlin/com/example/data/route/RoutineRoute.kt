package com.example.data.route

import com.example.data.entity.RoutineEntity
import com.example.data.util.GenericResponse
import com.example.domain.model.RoutineModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.routeRoutine() {

    routing {

        // CREATE
        post("/routine-register") {
            try {
                val routineModel = call.receive<RoutineModel>()

                val routineId = transaction {
                    RoutineEntity.insert {
                        it[name] = routineModel.name
                        it[description] = routineModel.description
                        it[goal] = routineModel.goal
                    } get RoutineEntity.id
                }

                if (routineId != null) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "Routine registered successfully with ID: $routineId")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        GenericResponse(isSuccess = false, data = "Failed to register the routine")
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    GenericResponse(isSuccess = false, data = "Internal server error: ${e.message}")
                )
            }
        }

        // READ
        get("/routines") {
            try {
                val routines = transaction {
                    RoutineEntity.selectAll().map {
                        RoutineModel(
                            name = it[RoutineEntity.name],
                            description = it[RoutineEntity.description],
                            goal = it[RoutineEntity.goal]
                        )
                    }
                }

                if (routines.isNotEmpty()) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = routines)
                    )
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = false, data = null)
                    )
                }

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    GenericResponse(isSuccess = false, data = "Internal server error: ${e.message}")
                )
            }
        }

        get("/routine/{routineId}") {
            val routineId = call.parameters["routineId"]?.toIntOrNull()
            if (routineId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid routine ID")
                )
                return@get
            }

            try {
                val routine = transaction {
                    RoutineEntity.select { RoutineEntity.id eq routineId }
                        .map {
                            RoutineModel(
                                name = it[RoutineEntity.name],
                                description = it[RoutineEntity.description],
                                goal = it[RoutineEntity.goal]
                            )
                        }
                        .singleOrNull()
                }

                if (routine != null) {
                    call.respond(HttpStatusCode.OK, routine)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "Routine not found")
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    GenericResponse(isSuccess = false, data = "Internal server error: ${e.message}")
                )
            }
        }

        // UPDATE
        put("/routine/{routineId}") {
            val id = call.parameters["routineId"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid ID")
                )
                return@put
            }

            try {
                val routineModel = call.receive<RoutineModel>()

                val updateRows = transaction {
                    RoutineEntity.update({ RoutineEntity.id eq id }) {
                        it[name] = routineModel.name
                        it[description] = routineModel.description
                        it[goal] = routineModel.goal
                    }
                }

                if (updateRows > 0) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "Routine updated successfully")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "Routine not found")
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    GenericResponse(isSuccess = false, data = "Internal server error: ${e.message}")
                )
            }
        }

        // DELETE
        delete("/routine/{routineId}") {
            val id = call.parameters["routineId"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid ID")
                )
                return@delete
            }

            try {
                val deleteRows = transaction {
                    RoutineEntity.deleteWhere { RoutineEntity.id eq id }
                }

                if (deleteRows > 0) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "Routine deleted successfully")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "Routine not found")
                    )
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