package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val profileImage: String
)