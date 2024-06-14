package com.example.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @field:SerializedName("id")
    val id : Int? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("last_name")
    val lastName: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("password")
    val password: String? = null,
    @field:SerializedName("profile_image")
    val profileImage: String? = null,
    @field:SerializedName("apikey")
    val apikey: String? = null
)