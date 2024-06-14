package com.example.data.route

import com.example.data.application.datasource.db.DbConnection
import com.example.data.entity.user.UserTable
import com.example.data.util.GenericResponse
import com.example.domain.model.UserModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.database.Database
import org.ktorm.dsl.*

fun Application.routeUser() {

    val db: Database = DbConnection.getDatabaseInstance()

    routing {
        get("/") {
            call.respondText("Welcome to Ktor-Fitness MySql")
        }

        // CREATE
        post("/register") {
            val user: UserModel = call.receive()
            val noOfRowsAffected = db.insert(table = UserTable) {
                set(it.name, user.name)
                set(it.lastName, user.lastName)
                set(it.email, user.email)
                set(it.password, user.password)
                set(it.profileImage, user.profileImage)
                set(it.apikey, user.apikey)
            }

            if (noOfRowsAffected > 0) {
                // Success
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                )
            } else {
                // Fail
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Error to register the user")
                )
            }
        }

        // READ
        get("/users") {
            val list = db.from(UserTable)
                .select()
                .map {
                    UserModel(
                        id = it[UserTable.id],
                        name = it[UserTable.name],
                        lastName = it[UserTable.lastName],
                        email = it[UserTable.email],
                        password = it[UserTable.password],
                        profileImage = it[UserTable.profileImage],
                        apikey = it[UserTable.apikey]
                    )
                }

            if (list.isNotEmpty()) {
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = list)
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = false, data = null)
                )
            }
        }

        get("user/{userId}") {
            val userIdStr = call.parameters["userId"]
            val userIdInt = userIdStr?.toInt() ?: -1

            val user = db.from(UserTable)
                .select()
                .where {
                    UserTable.id eq userIdInt
                }
                .map {
                    UserModel(
                        id = it[UserTable.id],
                        name = it[UserTable.name],
                        lastName = it[UserTable.lastName],
                        email = it[UserTable.email],
                        password = it[UserTable.password],
                        profileImage = it[UserTable.profileImage],
                        apikey = it[UserTable.apikey]
                    )
                }
                .firstOrNull()

            if (user != null) {
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = user)
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = false, data = null)
                )
            }
        }

        // UPDATE
        put("user/{userId}") {
            val userIdStr = call.parameters["userId"]
            val userIdInt = userIdStr?.toInt() ?: -1
            val userReq: UserModel = call.receive()

            val noOfRowsAffected = db.update(table = UserTable) {
                set(it.name, userReq.name)
                set(it.lastName, userReq.lastName)
                set(it.email, userReq.email)
                set(it.password, userReq.password)
                set(it.profileImage, userReq.profileImage)
                set(it.apikey, userReq.apikey)

                where {
                    it.id eq userIdInt
                }
            }

            if (noOfRowsAffected > 0) {
                // Success
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                )
            } else {
                // Fail
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Error to update the user")
                )
            }
        }

        // DELETE
        delete("user/{userId}") {
            val userIdStr = call.parameters["userId"]
            val userIdInt = userIdStr?.toInt() ?: -1

            val noOfRowsAffected = db.delete(table = UserTable) {
                it.id eq userIdInt
            }

            if (noOfRowsAffected > 0) {
                // Success
                call.respond(
                    HttpStatusCode.OK,
                    GenericResponse(isSuccess = true, data = "$noOfRowsAffected rows are affected")
                )
            } else {
                // Fail
                call.respond(
                    HttpStatusCode.BadRequest,
                    GenericResponse(isSuccess = false, data = "Error to delete the user")
                )
            }
        }
    }
}