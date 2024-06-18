package com.example.data.repository

import com.example.data.entity.UserEntity
import com.example.domain.model.UserModel
import com.example.domain.repository.UserRepository
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override fun findById(userId: Int): UserModel? {
        return transaction {
            UserEntity.select { UserEntity.id eq userId }
                .map {
                    UserModel(
                        name = it[UserEntity.name],
                        lastName = it[UserEntity.lastName],
                        email = it[UserEntity.email],
                        password = it[UserEntity.password],
                        profileImage = it[UserEntity.profileImage]
                    )
                }
                .singleOrNull()
        }
    }
}