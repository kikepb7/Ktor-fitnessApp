package com.example.data.route

import com.example.data.entity.RoutineEntity
import com.example.data.entity.UserEntity
import com.example.data.util.GenericResponse
import com.example.domain.model.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.routeUser() {

    routing {
        get("/") {
            call.respondText("Welcome to Ktor-Fitness MySql")
        }

        // CREATE
        post("/user-register") {
            try {
                val userModel = call.receive<UserModel>()

                val userId = transaction {
                    UserEntity.insert {
                        it[name] = userModel.name
                        it[lastName] = userModel.lastName
                        it[email] = userModel.email
                        it[password] = userModel.password
                        it[profileImage] = userModel.profileImage
                    } get UserEntity.id
                }

                if (userId != null) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "User registered successfully with ID: $userId")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        GenericResponse(isSuccess = false, data = "Failed to register the user")
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
        get("/users") {
            try {
                val users = transaction {
                    UserEntity.selectAll().map {
                        UserModel(
                            name = it[UserEntity.name],
                            lastName = it[UserEntity.lastName],
                            email = it[UserEntity.email],
                            password = it[UserEntity.password],
                            profileImage = it[UserEntity.profileImage]
                        )
                    }
                }

                if (users.isNotEmpty()) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = users)
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

        get("/user/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            if (userId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid user ID")
                )
                return@get
            }

            try {
                val user = transaction {
                    UserEntity.select { UserEntity.id eq userId }
                        .map {
                            UserModel(
                                name = it[UserEntity.name],
                                lastName = it[UserEntity.lastName],
                                email = it[UserEntity.email],
                                password = it[UserEntity.password],
                                profileImage = it[UserEntity.profileImage]
                            )
                        }
                        .singleOrNull()
                }

                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "User not found")
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
        put("/user/{userId}") {
            val id = call.parameters["userId"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid ID")
                )
                return@put
            }

            try {
                val userModel = call.receive<UserModel>()

                val updateRows = transaction {
                    RoutineEntity.update({ UserEntity.id eq id }) {
                        it[UserEntity.name] = userModel.name
                        it[UserEntity.lastName] = userModel.lastName
                        it[UserEntity.email] = userModel.email
                        it[UserEntity.password] = userModel.password
                        it[UserEntity.profileImage] = userModel.profileImage
                    }
                }

                if (updateRows > 0) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "User updated successfully")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "User not found")
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
        delete("/user/{userId}") {
            val id = call.parameters["userId"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Invalid ID")
                )
                return@delete
            }

            try {
                val deleteRows = transaction {
                    UserEntity.deleteWhere { UserEntity.id eq id }
                }

                if (deleteRows > 0) {
                    call.respond(
                        HttpStatusCode.OK,
                        GenericResponse(isSuccess = true, data = "User deleted successfully")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        GenericResponse(isSuccess = false, data = "User not found")
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