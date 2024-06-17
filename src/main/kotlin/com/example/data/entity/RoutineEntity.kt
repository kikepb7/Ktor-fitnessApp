package com.example.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object RoutineEntity : IntIdTable(name = "routine") {
    val name = text("name").uniqueIndex()
    val description = varchar("description", 255).uniqueIndex()
    val goal = text("goal").uniqueIndex()
}