package com.example.data.repository

import com.example.data.entity.UserEntity
import com.example.domain.model.UserModel
import com.example.domain.repository.UserRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override fun registerUser(userModel: UserModel): Int {
        return transaction {
            UserEntity.insertAndGetId {
                it[name] = userModel.name
                it[lastName] = userModel.lastName
                it[email] = userModel.email
                it[password] = userModel.password
                it[profileImage] = userModel.profileImage
            }.value
        }
    }

    override fun findUsers(): List<UserModel> {
        return transaction {
            UserEntity.selectAll().map {
                UserModel(
                    name = it[UserEntity.name],
                    lastName = it[UserEntity.lastName],
                    email = it[UserEntity.email],
                    password = it[UserEntity.password],
                    profileImage = it[UserEntity.profileImage]
                )
            }
        }
    }

    override fun findUserById(userId: Int): UserModel? {
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

    override fun updateUserById(userId: Int, userModel: UserModel): Boolean {
        return transaction {
            val updateRows = UserEntity.update({ UserEntity.id eq userId }) {
                it[name] = userModel.name
                it[lastName] = userModel.lastName
                it[email] = userModel.email
                it[password] = userModel.password
                it[profileImage] = userModel.profileImage
            }
            updateRows > 0
        }
    }

    override fun deleteUserById(userId: Int): Boolean {
        return transaction {
            val deleteRows = UserEntity.deleteWhere { UserEntity.id eq userId }

            deleteRows > 0
        }
    }
}