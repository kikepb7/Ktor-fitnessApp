package com.example.domain.repository

import com.example.domain.model.RoutineModel

interface RoutineRepository {
    fun registerRoutine(routineModel: RoutineModel): Int
    fun findRoutines(): List<RoutineModel>?
    fun findRoutineById(routineId: Int): RoutineModel?
    fun updateRoutineById(routineId: Int, routineModel: RoutineModel): Boolean
    fun deleteRoutineById(routineId: Int): Boolean
}