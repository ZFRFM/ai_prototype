package com.example.prototype.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<TaskDomain>>

    suspend fun insertTask(title: String)

    suspend fun setTaskCompleted(id: Long, isCompleted: Boolean)

    suspend fun clearCompletedTasks()
}
