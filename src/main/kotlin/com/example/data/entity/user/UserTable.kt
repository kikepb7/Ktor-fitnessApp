package com.example.data.entity.user

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.varchar

//import org.jetbrains.exposed.sql.jodatime.datetime

object UserTable: Table<Nothing>(tableName = "user") {
    val id = int(name = "id").primaryKey()
    val name = varchar(name = "name")
    val lastName = varchar(name = "last_name")
    val email = varchar(name = "email")
    val password = varchar(name = "password")
    val profileImage = varchar(name = "profile_image")
    val apikey = varchar(name = "apikey")
}

