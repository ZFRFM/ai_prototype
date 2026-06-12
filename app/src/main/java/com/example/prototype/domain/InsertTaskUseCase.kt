package com.example.prototype.domain

class InsertTaskUseCase(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(title: String) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return

        taskRepository.insertTask(trimmedTitle)
    }
}
