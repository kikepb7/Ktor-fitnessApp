package com.example.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object UserRoutineEntity : IntIdTable(name = "user_routine") {
    val userId = integer(name = "user_id").references(UserEntity.id)
    val routineId = integer(name = "routine_id").references(RoutineEntity.id)
}