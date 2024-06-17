package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RoutineModel(
    val name: String,
    val description: String,
    val goal: String
)