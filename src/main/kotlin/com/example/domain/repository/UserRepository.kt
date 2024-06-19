package com.example.domain.repository

import com.example.domain.model.UserModel

interface UserRepository {
    fun registerUser(userModel: UserModel): Int
    fun findUsers(): List<UserModel>?
    fun findUserById(userId: Int): UserModel?
    fun updateUserById(userId: Int, userModel: UserModel): Boolean
    fun deleteUserById(userId: Int): Boolean
}