package com.example.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.ktorm.entity.Entity

interface UserRoutine : Entity<UserRoutine> {
    val id: Int
    val userId: Int
    val routineId: Int

    companion object : Entity.Factory<UserRoutine>()
}

object UserRoutineEntity : IntIdTable(name = "user_routine") {
    val userId = integer(name = "user_id").references(UserEntity.id)
    val routineId = integer(name = "routine_id").references(RoutineEntity.id)
}