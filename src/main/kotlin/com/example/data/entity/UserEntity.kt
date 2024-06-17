package com.example.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object UserEntity : IntIdTable(name = "user") {
    val name = varchar(name = "name", 50).uniqueIndex()
    val lastName = varchar(name = "last_name", 50).uniqueIndex()
    val email = varchar(name = "email", 100).uniqueIndex()
    val password = varchar(name = "password", 255).uniqueIndex()
    val profileImage = varchar(name = "profile_image", 255).uniqueIndex()
}