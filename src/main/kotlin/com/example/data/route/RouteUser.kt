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
import org.ktorm.dsl.insert

fun Application.routeUser() {

    val db: Database = DbConnection.getDatabaseInstance()

    routing {
        get("/") {
            call.respondText("Werlcome to Ktor MySql")
        }

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
                    GenericResponse(isSuccess = true, data = "Error to register the user")
                )
            }
        }
    }
}