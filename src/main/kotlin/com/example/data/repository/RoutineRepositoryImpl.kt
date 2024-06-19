package com.example.data.repository

import com.example.data.entity.RoutineEntity
import com.example.domain.model.RoutineModel
import com.example.domain.repository.RoutineRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class RoutineRepositoryImpl : RoutineRepository {
    override fun registerRoutine(routineModel: RoutineModel): Int {
        return transaction {
            RoutineEntity.insertAndGetId {
                it[name] = routineModel.name
                it[description] = routineModel.description
                it[goal] = routineModel.goal
            }.value
        }
    }

    override fun findRoutines(): List<RoutineModel> {
        return transaction {
            RoutineEntity.selectAll().map {
                RoutineModel(
                    name = it[RoutineEntity.name],
                    description = it[RoutineEntity.description],
                    goal = it[RoutineEntity.goal]
                )
            }
        }
    }

    override fun findRoutineById(routineId: Int): RoutineModel? {
        return transaction {
            RoutineEntity.select { RoutineEntity.id eq routineId }
                .map {
                    RoutineModel(
                        name = it[RoutineEntity.name],
                        description = it[RoutineEntity.description],
                        goal = it[RoutineEntity.goal]
                    )
                }
                .singleOrNull()
        }
    }

    override fun updateRoutineById(routineId: Int, routineModel: RoutineModel): Boolean {
        return transaction {
            val updateRows =
                RoutineEntity.update({ RoutineEntity.id eq routineId }) {
                    it[name] = routineModel.name
                    it[description] = routineModel.description
                    it[goal] = routineModel.goal
                }
            updateRows > 0
        }
    }

    override fun deleteRoutineById(routineId: Int): Boolean {
        return transaction {
            val deleteRows = RoutineEntity.deleteWhere { RoutineEntity.id eq routineId }

            deleteRows > 0
        }
    }
}