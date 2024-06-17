package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRoutineModel(
    val userId: Int,
    val routineId: Int
)