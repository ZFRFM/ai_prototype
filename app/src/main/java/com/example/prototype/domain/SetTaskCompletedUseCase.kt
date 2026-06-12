package com.example.prototype.domain

class SetTaskCompletedUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: Long, isCompleted: Boolean) {
        taskRepository.setTaskCompleted(id, isCompleted)
    }
}
