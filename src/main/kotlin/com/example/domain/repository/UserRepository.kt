package com.example.domain.repository

import com.example.domain.model.UserModel

interface UserRepository {
    fun findById(userId: Int): UserModel?
}