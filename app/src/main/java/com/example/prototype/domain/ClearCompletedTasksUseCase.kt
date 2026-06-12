package com.example.prototype.domain

class ClearCompletedTasksUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke() {
        taskRepository.clearCompletedTasks()
    }
}
