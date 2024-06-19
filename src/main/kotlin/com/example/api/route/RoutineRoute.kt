package com.example.api.route

import com.example.api.response.GenericResponse
import com.example.data.repository.RoutineRepositoryImpl
import com.example.domain.model.RoutineModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.routeRoutine(
    routineRepositoryImpl: RoutineRepositoryImpl
) {

    routing {

        // CREATE
        post("/routine-register") {
            try {
                val routineModel = call.receive<RoutineModel>()
                val routineId = routineRepositoryImpl.registerRoutine(routineModel = routineModel)

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
                val routines = routineRepositoryImpl.findRoutines()

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
                val routine = routineRepositoryImpl.findRoutineById(routineId = routineId)

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
                val routineUpdated =
                    routineRepositoryImpl.updateRoutineById(routineId = id, routineModel = routineModel)

                if (routineUpdated) {
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
                val deleteRows = routineRepositoryImpl.deleteRoutineById(routineId = id)

                if (deleteRows) {
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