package com.example.prototype.domain

import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<List<TaskDomain>> {
        return taskRepository.observeTasks()
    }
}
