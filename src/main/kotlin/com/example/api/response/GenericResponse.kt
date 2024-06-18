package com.example.api.response

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<out T>(val isSuccess: Boolean, val data: T)